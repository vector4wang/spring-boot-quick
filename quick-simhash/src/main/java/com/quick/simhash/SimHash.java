package com.quick.simhash;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/22
 * Time: 11:05
 * Description:
 */
public class SimHash {

    private int HASH_LENGTH = 32;

    /**
     * In this method, use int(32 bits) to store hashcode.
     * use 1 as all words weight for simple reason.
     * use Hamming distance as hashcode distance.
     *
     * @author arthur503
     */
    public SimHash(){

    }

    public int compareString(String str1, String str2){
        System.out.println("SimHash compare string of: \""+str1+"\" AND \""+str2+"\"");
        int hash1 = getHashCode(str1);
        int hash2 = getHashCode(str2);

        int distance = getDistance(hash1, hash2);
        System.out.println("SimHash string distance of: \""+str1+"\" AND \""+str2+"\" is:"+distance);
        return distance;
    }

    /**
     * Use hamming distance in this method.
     * Can change to other distance like Euclid distance or p-distance, etc.
     * @param hash1
     * @param hash2
     * @return
     */
    private int getDistance(int hash1, int hash2) {
        // TODO Auto-generated method stub
        int distance = 0;
        for(int i=0;i<HASH_LENGTH;i++){
            int bit1 = hash1 & (1 << i);
            int bit2 = hash2 & (1 << i);
            if(bit1 != bit2){
                distance ++;
            }
        }
//		System.out.println("Distance of hash1 and hash2 is:"+distance);
        return distance;
    }

    private int getHashCode(String str) {
        // TODO Auto-generated method stub
        int result = 0;

        //Step One: Expand.
        String[] array = str.split(" ");
//		System.out.println("array length:"+array.length+". array0 is:"+array[0]);
        int hash = 0;
        int[] hashBits = new int[HASH_LENGTH];
        for(int i=0;i<array.length;i++){
            hash = array[i].hashCode();
            System.out.println("String \""+array[i]+"\""+" hashcode is:"+Integer.toBinaryString(hash));
            for(int j=HASH_LENGTH-1;j>=0;j--){
                int bit = (hash >> j) & 1;
//				System.out.println("shift j is:"+j+" bit is:"+bit);

                //Different keyword may have different weight. add or minus their weight here.
                //For simple reason, all weight are assigned as 1 in this method.
                if(bit == 1){
                    hashBits[HASH_LENGTH-1-j]++;
                }else{
                    hashBits[HASH_LENGTH-1-j]--;
                }

            }

            //print hashbits for debug.
/*			System.out.println("hashbits is:");
			for(int k=0;k<HASH_LENGTH;k++){
				System.out.println("k="+k+" "+hashBits[k]);
			}*/
        }

        //Step Two: Shrink.
        for(int i=0;i<HASH_LENGTH;i++){
            int bit = hashBits[i] > 0 ? 1 : 0;
            if(bit == 1){
                result |= 1 << (HASH_LENGTH-1-i);
            }
        }
        System.out.println("String \""+str+ "\" hashcode is:"+result
                +". Binary format is: "+Integer.toBinaryString(result));
        return result;
    }

}
