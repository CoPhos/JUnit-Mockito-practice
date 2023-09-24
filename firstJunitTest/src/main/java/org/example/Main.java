package org.example;

public class Main {
    public static void main(String[] args) {
	int[] arr = {10, 20, 30, 40, 50, 60, 70, 80, 90};
    int ele = 60;

    int foundIndex = jumpSearch(arr, ele);
    System.out.println(foundIndex > 0 ? "Found at index : " + foundIndex : "Element Not Found");
  }

  public static int jumpSearch(int[] arr, int ele) {

    int prev = 0;
    int n = arr.length;
    int step = (int) Math.floor(Math.sqrt(n));

    //loop until current element is less than the given search element
    while (arr[Math.min(step, n) - 1] < ele) {
      prev = step;
      step += (int) Math.floor(Math.sqrt(n));
      if (prev >= n) return -1;
    }

    //perform linear search prev index element to given element
    while (arr[prev] < ele) {
      prev++;
      if (prev == Math.min(step, n)) return -1;
    }

    // Return index if element is found
    if (arr[prev] == ele) return prev;

    return -1;
    }


    // declaring double type variables
    Double n1 = -1.0, n2 = 4.5, n3 = -5.3, largest;

    // checks if n1 is greater than or equal to n2
    if (n1 >= n2) {

      // if...else statement inside the if block
      // checks if n1 is greater than or equal to n3
      if (n1 >= n3) {
        largest = n1;
      }

      else {
        largest = n3;
      }
    } else {

      // if..else statement inside else block
      // checks if n2 is greater than or equal to n3
      if (n2 >= n3) {
        largest = n2;
      }

      else {
        largest = n3;
      }
    }

    System.out.println("Largest Number: " + largest);
  
    }
      String onum = "157";
 
        // octal to decimal using Integer.parseInt()
        int num = Integer.parseInt(onum, 8);
 
        System.out.println(
            "Decimal equivalent of Octal value 157 is: "
            + num);
     // Creating an ArrayList
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        // Using min()
        int min = Collections.min(numbers);
        System.out.println("Minimum Element: " + min);

        // Using max()
        int max = Collections.max(numbers);
        System.out.println("Maximum Element: " + max);}
}
