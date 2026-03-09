package PrefixSum;

public class PassingCars {
    public static void main(String[] args) {
        System.out.println(new PassingCars().passingCar(new int[]{0,1,0,1,1}));
        System.out.println(new PassingCars().passingCar(new int[]{0,1,0,0,1}));
    }
    public int passingCar(int[] arr){
        long countEast = 0;
        long count = 0;
        for(int a:arr){
            if(a==0){
                countEast++;
            }
            else{
                count+=countEast;
            }
        }
        return (int)count;
    }
}
