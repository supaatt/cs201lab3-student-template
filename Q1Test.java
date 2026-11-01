import java.util.*;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 


     static class Node {
        int val;
        Node left, right;
        Node(int v) { val = v; }
    }
 
    static Node insert(Node root, int v) {
        if (root == null) return new Node(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }
 
    static void inorder(Node n, List<Integer> out) {
        if (n == null) return;
        inorder(n.left, out); out.add(n.val); inorder(n.right, out);
    }
 
    static void preorder(Node n, List<Integer> out) {
        if (n == null) return;
        out.add(n.val); preorder(n.left, out); preorder(n.right, out);
    }
 
    static void postorder(Node n, List<Integer> out) {
        if (n == null) return;
        postorder(n.left, out); postorder(n.right, out); out.add(n.val);
    }
 
    static int[] parse(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        String[] parts = s.trim().split("-");
        int[] a = new int[parts.length];
        try {
            for (int i = 0; i < parts.length; i++) a[i] = Integer.parseInt(parts[i].trim());
        } catch (NumberFormatException e) {
            return null;
        }
        return a;
    }
 
    static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] >= a[i]) return false;
        return true;
    }
 
    static boolean matches(List<Integer> list, int[] a) {
        if (list.size() != a.length) return false;
        for (int i = 0; i < a.length; i++) if (list.get(i) != a[i]) return false;
        return true;
    }
 
    // Try: arr[inIdx] is inorder, arr[preIdx] is preorder, arr[postIdx] is postorder
    static boolean tryAssign(int[][] arr, int inIdx, int preIdx, int postIdx) {
        if (!isSorted(arr[inIdx])) return false;
 
        Node root = null;
        for (int v : arr[preIdx]) root = insert(root, v);
 
        List<Integer> in = new ArrayList<>(), pre = new ArrayList<>(), post = new ArrayList<>();
        inorder(root, in);
        preorder(root, pre);
        postorder(root, post);
 
        return matches(in, arr[inIdx]) && matches(pre, arr[preIdx]) && matches(post, arr[postIdx]);
    }

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){

          int[][] arr = { parse(traversal1), parse(traversal2), parse(traversal3) };
 
        for (int[] a : arr) {
            if (a == null || a.length != arr[0].length) {
                return "Invalid traversals";
            }
        }
 
        String[] names = new String[3];
        int[][] perms = { {0,1,2}, {0,2,1}, {1,0,2}, {1,2,0}, {2,0,1}, {2,1,0} }; // {in, pre, post}
 
        for (int[] p : perms) {
            if (tryAssign(arr, p[0], p[1], p[2])) {
                names[p[0]] = "Inorder";
                names[p[1]] = "Preorder";
                names[p[2]] = "Postorder";
                return "Traversal 1 - " + names[0]
                        + ", Traversal 2 - " + names[1]
                        + ", Traversal 3 - " + names[2];
            }
        }
        return "Invalid traversals";
     }
}