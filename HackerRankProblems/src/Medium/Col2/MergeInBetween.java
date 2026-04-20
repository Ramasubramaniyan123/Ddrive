package Medium.Col2;

class SinglyLinkedListNode {
    public int data;
    public SinglyLinkedListNode next;

    public SinglyLinkedListNode(int nodeData) {
        this.data = nodeData;
        this.next = null;
    }
}

class SinglyLinkedList {
    public SinglyLinkedListNode head;
    public SinglyLinkedListNode tail;

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insertNode(int nodeData) {
        SinglyLinkedListNode node = new SinglyLinkedListNode(nodeData);

        if (this.head == null) {
            this.head = node;
        } else {
            this.tail.next = node;
        }

        this.tail = node;
    }
}


public class MergeInBetween {
    class Result {

        public static SinglyLinkedListNode mergeInBetween(
                SinglyLinkedListNode list1,
                SinglyLinkedListNode list2,
                int i,
                int j
        ) {
            SinglyLinkedListNode prev = null;
            SinglyLinkedListNode curr = list1;

            // Step 1: reach (i-1)th node
            for (int idx = 1; idx < i; idx++) {
                prev = curr;
                curr = curr.next;
            }

            // Step 2: reach jth node
            for (int idx = i; idx <= j; idx++) {
                curr = curr.next;
            }

            // curr is now (j+1)th node
            SinglyLinkedListNode after = curr;

            // Step 3: connect prev → list2
            if (prev != null) {
                prev.next = list2;
            } else {
                list1 = list2; // if i == 1
            }

            // Step 4: find tail of list2
            SinglyLinkedListNode tail = list2;
            while (tail.next != null) {
                tail = tail.next;
            }

            // Step 5: connect tail → after
            tail.next = after;

            return list1;
        }
    }
}
