package com.ray.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Math {
    
    public static List<Integer> permutation(int[] nums) {
        Arrays.sort(nums);
        List<Integer> rs = new ArrayList<Integer>();
        permutation(rs, 0, nums);
        return rs;
    }
    
    public static void permutation(List<Integer> rs, int base, int[] nums) {
        
        if (nums.length == 0) {
            rs.add(base);
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            int[] newNums = new int[nums.length - 1];
            
            for (int j = 0; j < newNums.length; j++) {
                if (j < i)
                    newNums[j] = nums[i];
                else
                    newNums[j] = nums[i+1];
            }
            permutation(rs, base*10 + nums[i], newNums);
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
