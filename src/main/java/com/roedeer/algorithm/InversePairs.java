package com.roedeer.algorithm;

/**
 * @Description 数组中的逆序对
 * @Author Roedeer
 * @Date 9/26/2019 19:11 AM
 **/
public class InversePairs {

    public static void main(String[] args) {
        InversePairs inversePairs = new InversePairs();
        int[] array = {7,5,6,4};
        System.out.println(inversePairs.inversePairs(array));
    }

    public int inversePairs(int[] array) {
        if (array == null)
            return 0;
        int[] copy = array.clone();
        return mergeSort(array, copy, 0, array.length - 1);
    }

    private int mergeSort(int[] array, int[] result, int start, int end) {
        if (start == end) {
            result[start] = array[start];
            return 0;
        }
        int length = (end - start)/2;
        int left = mergeSort(result, array, start, start + length);
        int right = mergeSort(result, array, start + length + 1, end);
        int leftIndex = start + length;
        int rightIndex = end;
        int count = 0;
        int point = rightIndex;
        while (leftIndex >= start && rightIndex >= start + length + 1) {
            if (array[leftIndex] > array[rightIndex]) {
                result[point--] = array[leftIndex--];
                count += rightIndex - start - length;
            } else {
                result[point--] = array[rightIndex--];
            }
        }
        for (int i = leftIndex; i >= start; i--) {
            result[point--] = array[i];
        }
        for (int j = rightIndex; j >= start + length + 1; j--) {
            result[point--] = array[j];
        }
        return left+right+count;
    }
}
