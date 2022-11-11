/*
 * name: Daniel Gil
 * purpose: to create a PatientQueue ADT that is
 * implemented using a min heap. so the element
 * with the highest priority is stored at the front
 * of the queue. giving us extremely fast dequeue operations.
 */
public class PatientQueue {

    private MyBinaryMinHeap patientQueue;

    public PatientQueue() {
        patientQueue = new MyBinaryMinHeap();
    }

    /*
     * add the given person into heap. bubbles up
     * the given patient to keep heap order.
     * duplicate names and priorities are allowed.
     * duplicate priorities will be sorted by name alphabetically
     */
    public void enqueue(String name, int priority) {
        patientQueue.enqueue(name, priority);
    }

    /*
     * add the given person into heap. bubbles up
     * the given patient to keep heap order.
     * duplicate names and priorities are allowed.
     * duplicate priorities will be sorted by name alphabetically
     */
    public void enqueue(Patient patient) {
        patientQueue.enqueue(patient.name, patient.priority);
    }

    /*
     * removes the frontmost patient in heap.
     * returns their name as a string.
     * 
     * if the heap is empty it returns an exception
     */
    public String dequeue() throws ArrayIndexOutOfBoundsException {
        return patientQueue.dequeue();
    }

    /*
     * modifies the priority of given name patient. to
     * the given priority prio. it will keep heap
     * order so after changing priority it will
     * bubble down or up depending if prio went lower or higher.
     */
    public void changePriority(String name, int newPriority) {
        patientQueue.changePriority(name, newPriority);
    }

    /*
     * return the name of the front most patient
     */
    public String peek() {
        return patientQueue.peek();
    }

    /*
     * return the integer priority of the front most patient
     */
    public int peekPriority() {
        return patientQueue.peekPriority();
    }

    /*
     * return true if patient heap does not contain elements
     */
    public boolean isEmpty() {
        return patientQueue.isEmpty();
    }

    /*
     * return the number of elements in heap
     */
    public int size() {
        return patientQueue.size();
    }

    /*
     * remove all elements from heap
     */
    public void clear() {
        patientQueue.clear();
    }

    @Override
    public String toString() {
        return patientQueue.toString();
    }

    /*
     * purpose: to implement patient queue class
     * using a binary min heap. this nested class contains
     * all the code and functions that are used in the patientqueue class
     * giving the actual patientqueue class a cleaner look.
     * 
     * also for practice on oop programming.
     */
    private class MyBinaryMinHeap {
        private Patient[] heap;
        private int size;
        private int heapCapacity;

        public MyBinaryMinHeap() {
            heapCapacity = 10;
            size = 0;
            heap = new Patient[heapCapacity];
        }

        /*
         * add the given person into heap. bubbles up
         * the given patient to keep heap order.
         * duplicate names and priorities are allowed.
         * duplicate priorities will be sorted by name alphabetically
         */
        public void enqueue(String name, int priority) {
            if (size + 1 >= heapCapacity)
                resizeHeap();
            Patient malito = new Patient(name, priority);
            heap[size + 1] = malito;
            size++;
            bubbleUp(size);
        }

        /*
         * removes the frontmost patient in heap.
         * returns their name as a string.
         * 
         * if the heap is empty it returns an exception
         */
        public String dequeue() throws ArrayIndexOutOfBoundsException {
            if (size == 0)
                throw new ArrayIndexOutOfBoundsException("Empty array.");
            String sickOne = heap[1].name;
            heap[1] = heap[size];
            heap[size] = null;
            size--;
            bubbleDown(1);
            return sickOne;
        }

        /*
         * return the name of the front most patient
         */
        public String peek() {
            return heap[1].name;
        }

        /*
         * return the integer priority of the front most patient
         */
        public int peekPriority() {
            return heap[1].priority;
        }

        /*
         * modifies the priority of given name patient. to
         * the given priority prio. it will keep heap
         * order so after changing priority it will
         * bubble down or up depending if prio went lower or higher.
         */
        public void changePriority(String name, int prio) {
            int oldPriority = 0;
            for (int i = 1; i <= size; i++) {
                if (name.equals(heap[i].name)) {
                    oldPriority = heap[i].priority;
                    heap[i].priority = prio;

                    if (oldPriority > prio) {
                        bubbleUp(i);
                    }
                    else if (oldPriority < prio) {
                        bubbleDown(i);
                    }
                    break;
                }
            }
        }

        /*
         * return true if patient heap does not contain elements
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /*
         * return the number of elements in heap
         */
        public int size() {
            return size;
        }

        /*
         * remove all elements from heap
         */
        public void clear() {
            heapCapacity = 10;
            size = 0;
            heap = new Patient[heapCapacity];
        }

        /*
         * moves the patient at the given index up in the heap
         * to main heap order. It checks with its parents to see
         * if the cur index has a higher or lower priority and
         * if parents have a lower priority then it moves the patient
         * up in the queue.
         */
        private void bubbleUp(int index) {
            int parentIndex = getParent(index);

            while (parentIndex != 0) {
                if (heap[index].priority < heap[parentIndex].priority) {
                    Patient sickOne = heap[parentIndex];
                    heap[parentIndex] = heap[index];
                    heap[index] = sickOne;
                    index = parentIndex;
                    parentIndex = getParent(index);
                }
                else if (heap[index].priority == heap[parentIndex].priority) {
                    // then compare which name comes first in alphabet
                    int compare = heap[index].name
                            .compareTo(heap[parentIndex].name);
                    if (compare < 0) {
                        Patient sickOne = heap[parentIndex];
                        heap[parentIndex] = heap[index];
                        heap[index] = sickOne;
                        index = parentIndex;
                        parentIndex = getParent(index);
                    }
                    else
                        return;
                }
                else
                    break;
            }
        }

        /*
         * moves the patient at the given index down the heap
         * to main heap order. it checks with both the children
         * to see which one if any have a higher priority than
         * their parent. if one of them do then they switch places
         * and it keeps finding.
         * if both children have the same priority then it checks
         * alphabetically.
         * 
         */
        private void bubbleDown(int index) {
            int leftChild = getLeftChild(index);
            int rightChild = getRightChild(index);

            while (heap[leftChild] != null || heap[rightChild] != null) {
                if (heap[leftChild] == null && heap[rightChild] != null) {
                    if (heap[index].priority > heap[rightChild].priority) {
                        // bubble to the right
                        Patient sickHolder = heap[rightChild];
                        heap[rightChild] = heap[index];
                        heap[index] = sickHolder;

                        // new indexes
                        index = rightChild;
                        leftChild = getLeftChild(index);
                        rightChild = getRightChild(index);
                    }
                    break;
                }
                else if (heap[leftChild] != null && heap[rightChild] == null) {
                    if (heap[index].priority > heap[leftChild].priority) {
                        // bubble to the left
                        Patient sickHolder = heap[leftChild];
                        heap[leftChild] = heap[index];
                        heap[index] = sickHolder;

                        // new indexes
                        index = leftChild;
                        leftChild = getLeftChild(index);
                        rightChild = getRightChild(index);
                    }
                    break;
                } else {
                    if (heap[index].priority > heap[leftChild].priority
                            || heap[index].priority > heap[rightChild].priority) {
                        if (heap[leftChild].priority < heap[rightChild].priority) {
                            // bubble to the left
                            Patient sickHolder = heap[leftChild];
                            heap[leftChild] = heap[index];
                            heap[index] = sickHolder;

                            // new indexes
                            index = leftChild;
                            leftChild = getLeftChild(index);
                            rightChild = getRightChild(index);
                        } else if (heap[rightChild].priority < heap[leftChild].priority) {
                            // bubble to the right
                            Patient sickHolder = heap[rightChild];
                            heap[rightChild] = heap[index];
                            heap[index] = sickHolder;

                            // new indexes
                            index = rightChild;
                            leftChild = getLeftChild(index);
                            rightChild = getRightChild(index);
                        } else {
                            // check alphabetical
                            int compare = heap[leftChild].name
                                    .compareTo(heap[rightChild].name);
                            if (compare < 0) {
                                // bubble to the left
                                Patient sickHolder = heap[leftChild];
                                heap[leftChild] = heap[index];
                                heap[index] = sickHolder;

                                // new indexes
                                index = leftChild;
                                leftChild = getLeftChild(index);
                                rightChild = getRightChild(index);
                            }
                            else if (compare > 0) {
                                // bubble to the right
                                Patient sickHolder = heap[rightChild];
                                heap[rightChild] = heap[index];
                                heap[index] = sickHolder;

                                // new indexes
                                index = rightChild;
                                leftChild = getLeftChild(index);
                                rightChild = getRightChild(index);
                            }
                            else
                                System.out.println("IDK WHAT TO DO");
                        }
                    }
                    else
                        break;

                }
                // is this right
                if (leftChild > heapCapacity || rightChild > heapCapacity)
                    break;
            }
        }

        /*
         * returns the left child of the given index
         */
        private int getLeftChild(int index) {
            return index * 2;
        }

        /*
         * returns the right child of the given index
         */
        private int getRightChild(int index) {
            return index * 2 + 1;
        }

        /*
         * returns the parent of the given index
         */
        private int getParent(int index) {
            return index / 2;
        }

        /*
         * resizes the heap to double its capacity
         */
        private void resizeHeap() {
            heapCapacity = heapCapacity * 2;

            Patient[] heapCopy = heap;
            heap = new Patient[heapCapacity];

            // copy values
            for (int i = 1; i <= size; i++) {
                heap[i] = heapCopy[i];
            }
        }

        /*
         * format of to string is: {Anat (4), Rein (6)}
         */
        @Override
        public String toString() {
            String big = "{";

            for (int i = 1; i <= size; i++) {
                big += heap[i].name + " (" + heap[i].priority + ")";
                if (i != size) {
                    big += ", ";
                }
            }
            big += "}";

            return big;
        }
    }
}
