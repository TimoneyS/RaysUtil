package com.ray.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Math {
    
    public static List<Integer> permutation(int[] nums) {
        Arrays.sort(nums);
        List<Integer> rs = new LinkedList<Integer>();
        permutation(rs, new LinkedList<Integer>(), nums, 0);
        return rs;
    }
    
    public static void permutation(List<Integer> list, List<Integer> ignore, int[] nums, int sum) {
        if (nums.length - ignore.size() == 1) {
            for (int i : nums)
                if (!ignore.contains(i)) list.add(sum*10+i);
            return;
        }
        
        for (int i : nums) {
            if (!ignore.contains(i)) {
                ignore.add(i);
                permutation(list, ignore, nums, sum*10 + i);
                ignore.remove((Integer)i);
            }
        }
        
    }
    
    public static void main(String args[]) {
        @SuppressWarnings("unused")
        List<Integer> rs;
        
        int size = 10;
        Timer.CLICK();
        rs = permutation(ArrayUtil.intArr(size));
        Timer.STOP();
        
    }

}
