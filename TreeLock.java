/*
 * This is where you will be implementing your TreeLock
 */
public class TreeLock {
	// binary tree represented by an array, ith node's children is on 2i+1 2i+2
	// and we do not record the leaf node for each thread in the array, since we do not need to add lock on it
	private PetersonNode[] lock_tree;
	private int length = 0;

	public TreeLock(int _numThr) {
		int i = 1;
		while(i < _numThr){
			length += i;
			i = i*2;
		}
		lock_tree = new PetersonNode[length];
		for(int j = 0; j < lock_tree.length; j++){
			lock_tree[j] = new PetersonNode(j);
		}
	}

	public void lock() {
		// Get the leaf node's index, is on the bottom layer of the tree
		// so we need to add other nodes in the tree
		int curr = ThreadID.get()+length;
		// lock from bottom to the top
		while(curr != 0){
			curr = (curr-1)/2;
			lock_tree[curr].lock(curr);
		}

	}

	public void unlock() {
		int curr = ThreadID.get()+length;
		// unlock from the bottom to the top
		while(curr != 0){
			curr = (curr-1)/2;
			lock_tree[curr].unlock(curr);
		}

	}
}
