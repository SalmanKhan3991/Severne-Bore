import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Data
public class Move {

    private int from;

    private int to;

    private int depth_charge;

    public static ArrayList<ArrayList<Mask>> positionList = new ArrayList<ArrayList<Mask>>();

    Move() {
        from = -1;
        to = -1;
        depth_charge = -1;
    }

    public static void initializeMasks() {

        int x = 16777216;

        while(x>0) {
            int p = getBitPosition(x);
//            System.out.println("Position="+p);
            ArrayList<Mask> maskList = new ArrayList<Mask>();
            for(int i=0; i<25; i++) {

                Mask mask = new Mask();
                if(p == i+1 ) {
                    if(!(p-1 <0) && p%5!=0) {
                        mask.maskValue = x*2;
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i-1) {
                    if(p+1 <= 25 &&  p%5!=4) {
                        mask.maskValue = x/2;
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i-5) {
                    if(p+5 <=25 ) {
                        mask.maskValue = x/(int)Math.pow(2,5);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+5) {
                    if (!(p-5 < 0)) {
                        mask.maskValue = x * (int) Math.pow(2, 5);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+4) {
                    if(!(p-4 < 0) && p%5 != 4) {
                        mask.maskValue = x*(int)Math.pow(2,4);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+6) {
                    if(!(p-6 < 0) && p%5 != 0) {
                        mask.maskValue = x*(int)Math.pow(2,6);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+8) {
                    if(!(p-8 < 0) && p%5 != 3 && p%5 != 4 ) {
                        mask.maskValue = x*(int)Math.pow(2,8);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+12) {
                    if(!(p-12 < 0)) {
                        mask.maskValue = x*(int)Math.pow(2,12);
                        mask.maskPosition = i;
                        
                    }
                }
                else if(p == i+18) {
                    if(!(p-18 < 0) && (p%5 == 3 || p%5 == 4)) {
                        mask.maskValue = x*(int)Math.pow(2,18);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+2) {
                    if(!(p-2 < 0) && (p%5 != 0 && p%5 != 1)) {
                        mask.maskValue = x*(int)Math.pow(2,2);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+3) {
                    if(!(p-3 < 0) && (p%5 == 3 || p%5 == 4)) {
                        mask.maskValue = x*(int)Math.pow(2,3);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+10) {
                    if(!(p-10 < 0)) {
                        mask.maskValue = x*(int)Math.pow(2,10);
                        mask.maskPosition = i;
                        
                    }
                } else if(p == i+15) {
                    if(!(p-15 < 0)) {
                        mask.maskValue = x*(int)Math.pow(2,15);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-2) {
                    if(p+2 <= 25 && p%5 != 3 && p%5 != 4) {
                        mask.maskValue = x/(int)Math.pow(2,2);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-3) {
                    if(p+3 <= 25 && (p%5 == 0 || p%5 == 1)) {
                        mask.maskValue = x/(int)Math.pow(2,3);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-4) {
                    if(p+4 <= 25 && p%5 != 0) {
                        mask.maskValue = x/(int)Math.pow(2,4);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-8) {
                    if(p+8 <= 25 && p%5 >= 2) {
                        mask.maskValue = x/(int)Math.pow(2,8);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-12) {
                    if(p+12 <= 25) {
                        mask.maskValue = x/(int)Math.pow(2,12);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-6) {
                    if(p+6 <= 25 && p%5 != 4) {
                        mask.maskValue = x/(int)Math.pow(2,6);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-18) {
                    if(p+18 <= 25 && (p%5 == 1 || p%5 == 0)) {
                        mask.maskValue = x/(int)Math.pow(2,18);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-10) {
                    if(p+10 <= 25) {
                        mask.maskValue = x/(int)Math.pow(2,10);
                        mask.maskPosition = i;
                        
                    }
                }
                  else if(p == i-15) {
                    if(p+15 <= 25) {
                        mask.maskValue = x/(int)Math.pow(2,15);
                        mask.maskPosition = i;
                        
                    }
                }
                if(mask.maskValue != -1) {
                    maskList.add(mask);
                }
            }
            x = x/2;
            positionList.add(maskList);

//            Printing the values
//            List<Mask> temp = positionList.get(counter);
//            int size = temp.size();
//            System.out.println(size);
//            for(int i=0; i<size;i++) {
//                System.out.println("Value = " + temp.get(i).maskValue + " Position = " + temp.get(i).maskPosition);
//
//            }
//            counter ++;
        }
    }

    public static int setBit(int value, int position, int toValue) {
        int mask = 1 << (25-position);
        return (value & ~mask) | ((toValue << (25-position)) & mask);
    }

    public static int getBitPosition(int value) {

        int count = 0;
        String zeros = "0000000000000000000000000";
        String binary = Integer.toBinaryString(value);
        String temp = zeros.substring(0,25-binary.length());
        binary = temp.concat(binary);

        for(int i=0; i<25; i++) {
            if(binary.charAt(i) == '0') {
                
            } else {
                break;
            }
        }
        return count;
    }

    public static int getValueForBitPosition(int position) {
        int value = 16777216;
        return value>>position;
    }

    public static boolean isPossibleMove(int from, int to, boolean isWhite) {
        int board = Board.getAmazonsBoard();
        int surfer = Board.getSurfer(true);
        int opponentSurfer = Board.getSurfer(false);
        if(!isWhite) {
            surfer = Board.getSurfer(false);
            opponentSurfer = Board.getSurfer(true);
        }

        if((surfer & from) > 0) {
            int fromPosition = getBitPosition(from);
            List<Mask> position = positionList.get(fromPosition);

            boolean found = false;
            int toPosition = -1;

            for (int i = 0; i < position.size(); i++) {
                if (position.get(i).maskValue == to) {
                    found = true;
                    toPosition = position.get(i).maskPosition;
                    break;
                }
            }

            if (!found) return false;

            int occupiedBoardPositions = surfer & opponentSurfer & board;

            int diff = toPosition - fromPosition;
            if (diff == 1 || diff == -1 || diff == 5 || diff == -5 || diff == 4 || diff == -4 || diff == 6 || diff == -6) {
                return !((to & occupiedBoardPositions) > 0);
            } else {

                switch (diff) {
                    case 2:
                        if (!((getValueForBitPosition(toPosition + 1) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 2) & occupiedBoardPositions) > 0))
                            return true;
                    case -2:
                        if (!((getValueForBitPosition(toPosition - 1) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 2) & occupiedBoardPositions) > 0))
                            return true;
                    case 10:
                        if (!((getValueForBitPosition(toPosition + 5) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 10) & occupiedBoardPositions) > 0))
                            return true;
                    case -10:
                        if (!((getValueForBitPosition(toPosition - 5) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 10) & occupiedBoardPositions) > 0))
                            return true;
                    case 8:
                        if (!((getValueForBitPosition(toPosition + 4) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 8) & occupiedBoardPositions) > 0))
                            return true;
                    case -8:
                        if (!((getValueForBitPosition(toPosition - 4) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 8) & occupiedBoardPositions) > 0))
                            return true;
                    case 12:
                        if ((!((getValueForBitPosition(toPosition + 6) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 12) & occupiedBoardPositions) > 0)) ||
                                (!((getValueForBitPosition(toPosition + 4) & occupiedBoardPositions) > 0) &&
                                        !((getValueForBitPosition(toPosition + 8) & occupiedBoardPositions) > 0) &&
                                        !((getValueForBitPosition(toPosition + 12) & occupiedBoardPositions) > 0)))
                            return true;
                    case -12:
                        if ((!((getValueForBitPosition(toPosition - 6) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 12) & occupiedBoardPositions) > 0)) ||
                                (!((getValueForBitPosition(toPosition - 4) & occupiedBoardPositions) > 0) &&
                                        !((getValueForBitPosition(toPosition - 8) & occupiedBoardPositions) > 0) &&
                                        !((getValueForBitPosition(toPosition - 12) & occupiedBoardPositions) > 0)))
                            return true;
                    case 3:
                        if ((!((getValueForBitPosition(toPosition + 1) & occupiedBoardPositions) > 0) &&
                                (!((getValueForBitPosition(toPosition + 2) & occupiedBoardPositions) > 0)) &&
                                (!((getValueForBitPosition(toPosition + 3) & occupiedBoardPositions) > 0))))
                            return true;
                    case -3:
                        if (!((getValueForBitPosition(toPosition - 1) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 1) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 1) & occupiedBoardPositions) > 0))
                            return true;
                    case 15:
                        if (!((getValueForBitPosition(toPosition + 5) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 10) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 15) & occupiedBoardPositions) > 0))
                            return true;
                    case -15:
                        if (!((getValueForBitPosition(toPosition - 5) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 10) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 15) & occupiedBoardPositions) > 0))
                            return true;
                    case 18:
                        if (!((getValueForBitPosition(toPosition + 6) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 12) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition + 18) & occupiedBoardPositions) > 0))
                            return true;
                    case -18:
                        if (!((getValueForBitPosition(toPosition - 6) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 12) & occupiedBoardPositions) > 0) &&
                                !((getValueForBitPosition(toPosition - 18) & occupiedBoardPositions) > 0))
                            return true;
                    default:
                        return false;
                }

            }
        }
        return false;
    }

    public static ArrayList<Move> possibleMoves(int from) {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        ArrayList<Mask> mask = positionList.get(getBitPosition(from));

        for(int i=0; i< mask.size(); i++) {
            int queenPosition = mask.get(i).maskPosition;
            ArrayList<Mask> depthChargerMask = positionList.get(queenPosition);
            if(isPossibleMove(from, mask.get(i).maskPosition, true)) {
                for(int j=0; j<depthChargerMask.size(); j++) {
                    if(isPossibleMove(queenPosition, depthChargerMask.get(j).maskPosition, true)) {
                        Move move = new Move();
                        move.from = from;
                        move.to = queenPosition;
                        move.depth_charge = depthChargerMask.get(j).maskPosition;
                        possibleMoves.add(move);
                    }
                }
            }
        }

        return possibleMoves;
    }

}
