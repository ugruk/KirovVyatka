public class Field {

    private char[][] field = new char[][]{ { 'V','Y','T','K','A' },{ 'X',' ','X',' ','X' },{ 'k','i','r','o','v' } };
    private char[][] answer = new char[][]{ { 'k','i','r','o','v' },{ 'X',' ','X',' ','X' },{ 'V','Y','T','K','A' } };
    private int[][] indexKruga = { { 0,1 },{ 1,1 },{ 2,1 },{ 2,2 },{ 2,3 },{ 1,3 },{ 0,3 },{ 0,2 } };
    private int[][] corners = { { 0,0 },{ 2,0 },{ 2,4 },{ 0,4 } };
    private int[][] cornersFriends = { { 0,1 },{ 2,1 },{ 2,3 },{ 0,3 } };
    private char[] round = new char[7];
    private char[] cornLetters = new char[4];
    public int numberOfMoves = 0;

    private void printOut() {
        for (int i = 0; i < 3; i++)
        {
           System.out.print("\n____________________\n");
            for (int j = 0; j < 5; j++)
                System.out.print("| " + field[i][j] + " ");
        }
        System.out.print("\n____________________\n");
    }

    public void Solve(){
        backward();
        //cornersGO();
        System.out.print("\nNumber of moves: " + numberOfMoves);
    }

    private void singleMove(int x1, int y1, int x2, int y2){
        char t = field[x1][y1];
        field[x1][y1] = field[x2][y2];
        field[x2][y2] = t;
        numberOfMoves++;
        printOut();
    }

    private void singleSpin(){
        boolean flag = true;
        int i = 0;
        while(flag && i < indexKruga.length){
            if (field[indexKruga[i][0]][indexKruga[i][1]] == ' '){
                if(field[indexKruga[(i+1) % indexKruga.length][0]][indexKruga[(i+1) % indexKruga.length][1]] != ' '){
                    singleMove(indexKruga[(i + 1) % indexKruga.length][0], indexKruga[(i + 1) % indexKruga.length][1],
                            indexKruga[i % indexKruga.length][0], indexKruga[i % indexKruga.length][1]);
                    flag = false;
                }
            }
            i++;
        }
    }

    private boolean isInRound(){  // Метод проверяет, есть ли в круге буква, которая может занять пустое место в углу
        for (int i = 0; i < corners.length; i++){
            if (field[corners[i][0]][corners[i][1]] == ' '){
                for (int j = 0; j < indexKruga.length; j++){
                    if (field[indexKruga[j][0]][indexKruga[j][1]] == answer[corners[i][0]][corners[i][1]]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void firstOut(){
        while(field[indexKruga[0][0]][indexKruga[0][1]] != ' ') {
            singleSpin();
        }
        singleMove(0,0,indexKruga[0][0],indexKruga[0][1]);
    }

    private void swapbois(char a, char b){
        System.out.println("trying to swap " + a + " and " + b);
        if (a != b) {
            while (!(field[indexKruga[0][0]][indexKruga[0][1]] == a && field[indexKruga[1][0]][indexKruga[1][1]] == b)) {
                singleSpin();
            }

            singleMove(indexKruga[0][0], indexKruga[0][1], 0, 0);
            while (!(field[indexKruga[7][0]][indexKruga[7][1]] == b && field[indexKruga[0][0]][indexKruga[0][1]] == ' ')) {
                singleSpin();
            }
            singleMove(0, 0, indexKruga[0][0], indexKruga[0][1]);
        }
    }

    private void swaplils(int a, int b){
        char t = round[a];
        round[a] = round[b];
        round[b] = t;
    }

    private void backward(){
        firstOut();
        boolean flag = true;
        for (int i = 0; i < indexKruga.length;i++){
            if (field[indexKruga[i][0]][indexKruga[i][1]] != ' ' && flag){
                round[i] = field[indexKruga[i][0]][indexKruga[i][1]];
            }
            else flag = false;
            if (field[indexKruga[i][0]][indexKruga[i][1]] != ' ' && !flag){
                round[i-1]= field[indexKruga[i][0]][indexKruga[i][1]];
            }
        }


        for (int i = 0; i < round.length - 1; i++){
            for(int j = 0; j < round.length - i - 1; j++){
                for(int p = 0; p < round.length; p++)
                    System.out.print(round[p]);
                swapbois(round[j],round[j+1]);
                swaplils(j,j+1);

            }
        }


    }

    private void cornersGO(){
        for(int i = 0;i < cornLetters.length; i++){
            cornLetters[i] = answer[corners[i][0]][corners[i][1]];
        }

        for(int i = 0; i < corners.length;i++){
            while (field[cornersFriends[i][0]][cornersFriends[i][1]] != answer[corners[i][0]][corners[i][1]]){
                singleSpin();
            }
            singleMove(cornersFriends[i][0],cornersFriends[i][1],corners[i][0],corners[i][1]);
            if (i < cornLetters.length - 1) {
                while (field[cornersFriends[i + 1][0]][cornersFriends[i + 1][1]] != ' ') {
                    singleSpin();
                }
                singleMove(corners[i+1][0], corners[i+1][1], cornersFriends[i + 1][0], cornersFriends[i][1]);
            }
        }

    }

    private void finishFinish(){

    }

}
