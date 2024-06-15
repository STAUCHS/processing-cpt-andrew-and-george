import processing.core.PApplet;
import processing.core.PImage;




public class Sketch extends PApplet {
  /**
   * game
   *
   * @author: George D.
   */
  // declare variables
  boolean blnLeft;
  boolean blnRight;
  boolean blnDown;
  boolean blnUp;
  boolean blnJump;
  boolean blnJumpCd;
  boolean blnClimb;
  boolean blnKey;
  boolean blnDash;
  boolean blnDashCd;
  boolean blnGravity = true;
  boolean blnNewLvl = true;
  boolean blnEnd = false;
  boolean blnTitle = true;
  int intDashing = -1;
  int intX;
  int intY;
  int intDashMax = 2;
  int intDashs; // number of remaining dashes
  int intStage = 0;
  int intMoveDisable;
  double dblSpdX;
  double dblSpdY;
  double dblStamina;
  int intDeathCount = 0;
  int intGameTick = 0;
  double dblDeathTime = -1;
  PImage imgPlayerRight;
  PImage imgPlayerLeft;
  PImage imgPlayerFor;
  PImage imgPlayerRightJump;
  PImage imgPlayerLeftJump;
  PImage imgTempPlayer;
  PImage imgPlayerLeftDash;
  PImage imgPlayerRightDash;
  PImage imgPlayerLeftClimb;
  PImage imgPlayerRightClimb;
  PImage imgDeath1;
  PImage imgDeath2;
  PImage imgDeath3;
  PImage imgDeath4;
  PImage imgDeath5;
  PImage imgDeath6;
  PImage imgDeath7;
  PImage imgDeath8;
  PImage imgDeath9;
  PImage imgDeath10;
  PImage imgStage0;
  PImage imgStage1;
  PImage imgStage2;
  PImage imgStage3;
  PImage imgStage4;
  PImage imgHat;
  PImage imgKey;
  PImage imgWin;
  PImage imgTitle;




  int[][] intSpawnPoint = {{45, 750, 1350, 600}, {60, 600, 30, 225}, {1310, 225, 203, 810}, {90, 90, 1350, 113}, {55, 820, -1, -1}}; // and
  int[][][] intHitMap = new int[6][33][21]; // and change first index to 5
  int[][] intPastPos = new int [12][2];




  public void settings() {
    size(1440, 900);
    System.out.println("JOEL WAS HERE HI MD CHEN");




  }




  public void setup() {
    // Sets Background Color
    background(120, 210, 255);
    intX = 60;
    intY = 60;
    dblSpdX = 0;
    dblSpdY = 0;
    stage0();
    stage1();
    stage2();
    stage3();
    stage4();
    frameRate(45);
    imgPlayerRight = loadImage("Explorer Right Facing.png");
    imgPlayerLeft = loadImage("Explorer Left Facing.png");
    imgPlayerFor = loadImage("Explorer Front Facing.png");
    imgPlayerRightJump = loadImage("Explorer Right Facing Jump.png");
    imgPlayerLeftJump = loadImage("Explorer Left Facing Jump.png");
    imgPlayerLeftClimb = loadImage("Explorer Left Facing Climb.png");
    imgPlayerRightClimb = loadImage("Explorer Right Facing Climb.png");
    imgPlayerRightDash = loadImage("Explorer Right Facing Dash.png");
    imgPlayerLeftDash = loadImage("Explorer Left Facing Dash.png");
    imgDeath1 = loadImage("Explorer Death Frame 1.png");
    imgDeath2 = loadImage("Explorer Death Frame 2.png");
    imgDeath3 = loadImage("Explorer Death Frame 3.png");
    imgDeath4 = loadImage("Explorer Death Frame 4.png");
    imgDeath5 = loadImage("Explorer Death Frame 5.png");
    imgDeath6 = loadImage("Explorer Death Frame 6.png");
    imgDeath7 = loadImage("Explorer Death Frame 7.png");
    imgDeath8 = loadImage("Explorer Death Frame 8.png");
    imgDeath9 = loadImage("Explorer Death Frame 9.png");
    imgDeath10 = loadImage("Explorer Death Frame 10.png");
    imgStage0 = loadImage("Level 1.png");
    imgStage1 = loadImage("Level 2.png");
    imgStage2 = loadImage("Level 3.png");
    imgStage3 = loadImage("Level 4.png"); // fix these!!!!!
    imgStage4 = loadImage("Level 5.png");
    imgHat = loadImage("Explorer Hat.png");
    imgWin = loadImage("Victory.png");
    imgKey = loadImage("Key.png");
    imgTitle = loadImage("Title Screen");
  }




  public void draw() {
    if (blnEnd){
      image(imgWin, 0, 0);
      text("You Died " + intDeathCount + "times!", 770, 300);
    } else if (blnTitle){
      intMoveDisable = 2;
      blnGravity = false;
      image(imgTitle, 0, 0);
    }
    screenShake();
    backdrop();
    //visualizeGrid();
    if (dblDeathTime == -1){
      if (dblSpdY < 15 && blnGravity) {
        dblSpdY += 1.4;
      }
      if (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1) {
        if (intDashing < 7){
          intDashs = intDashMax;
        }
        dblStamina = 125;
      }
     
      dashMechanic();
      walking();
      jumpMechanic();
      positionUpdate();
      drawPlayer();
      intGameTick++;
      if (intMoveDisable > 0){
        intMoveDisable--;
      }else if(intMoveDisable < 0){
        intMoveDisable++;
      }
    } else if((int)dblDeathTime == 0){ // and
      blnKey = false;
      if (intStage == 3){
        intStage--;
      }
      respawn();
      dblSpdX = 0;
      dblSpdY = 0;
      intDashs = 0;    
      dblDeathTime = -1;
    }else{
      switch ((int)dblDeathTime){
        case 10:
          image(imgDeath1, intX, intY - 44);
          break;
        case 9:
          image(imgDeath2, intX, intY - 44);
          break;
        case 8:
          image(imgDeath3, intX, intY - 44);  
          break;
        case 7:
          image(imgDeath4, intX, intY - 44);
          break;
        case 6:
          image(imgDeath5, intX, intY - 44);
          break;
        case 5:
          image(imgDeath6, intX, intY - 44);
          break;
        case 4:
          image(imgDeath7, intX, intY - 44);
          break;
        case 3:
          image(imgDeath8, intX, intY - 44);
          break;
        case 2:
          image(imgDeath9, intX, intY - 44);
          break;
        case 1:
          image(imgDeath10, intX, intY - 44);
          break;
      }
      dblDeathTime-=.334;
    }
  }
  public void backdrop(){
    if (intStage == 0){
      image(imgStage0, 0, 0);
      intDashMax = 0;
    } else if (intStage == 1){
      image(imgStage1, 0, 0);
      intDashMax = 1;
    } else if (intStage == 2){
      image(imgStage2, 0, 0);
      if (!blnKey){
        image(imgKey, 70, 430);
      }
    } else if (intStage == 3){
      image(imgStage3, 0, 0);
      if (intX > 1215 && blnKey){ // if have key and reach certain x door unlocks permanently
        intHitMap[3][29][0] = 0;
        intHitMap[3][29][1] = 0;
        intHitMap[3][29][2] = 0;
        intHitMap[3][29][3] = 0;
        blnKey = false;
      }
    } else if (intStage == 4){
      image(imgStage4, 0, 0);
    }
  }
  public void respawn(){
    if (blnNewLvl){
      intX = intSpawnPoint[intStage][0];
      intY = intSpawnPoint[intStage][1];
    } else {
      intX = intSpawnPoint[intStage][2];
      intY = intSpawnPoint[intStage][3];
    }
    dblSpdX = 0;
    dblSpdY = 0;
    intDashs = 0;
   
  }
  public void pause(long lngStart, long lngDuration){
    while (System.nanoTime() - lngStart < lngDuration){
      //null
    }
  }




  public void drawPlayer(){
    if (intDashing > -1 || dblSpdX + dblSpdY > 22 || dblSpdX + dblSpdY < -22 ){
      image(imgHat, intPastPos[(intGameTick + 1) % 12][0] + 20, intPastPos[(intGameTick + 1) % 12][1] - 30);
      image(imgHat, intPastPos[(intGameTick + 3) % 12][0] + 17, intPastPos[(intGameTick + 3) % 12][1] - 28);
      image(imgHat, intPastPos[(intGameTick + 5) % 12][0] + 15, intPastPos[(intGameTick + 5) % 12][1] - 26);
      image(imgHat, intPastPos[(intGameTick + 7) % 12][0] + 12, intPastPos[(intGameTick + 7) % 12][1] - 24);
      image(imgHat, intPastPos[(intGameTick + 9) % 12][0] + 10, intPastPos[(intGameTick + 9) % 12][1] - 22);
      image(imgHat, intPastPos[(intGameTick + 11) % 12][0] + 7, intPastPos[(intGameTick + 11) % 12][1] - 20);
    }
    if (blnKey){
      image(imgKey, intPastPos[(intGameTick + 7) % 12][0] + 12, intPastPos[(intGameTick + 7) % 12][1] - 24);
    }




    if(dblSpdX < 0 && intDashing > -1){
      imgTempPlayer = imgPlayerLeftDash;
    } else if(dblSpdX > 0 && intDashing > -1){
      imgTempPlayer = imgPlayerRightDash;
    } else if((detect(intX - 2, intY) == 1 || detect(intX - 2, intY - 44) == 1) && (dblSpdX > 0 || (blnClimb && dblStamina > 0)) ){
      imgTempPlayer = imgPlayerLeftClimb;
    } else if((detect(intX + 46, intY) == 1 || detect(intX + 46, intY - 44) == 1) && (dblSpdX < 0 || (blnClimb && dblStamina > 0)) ){
      imgTempPlayer = imgPlayerRightClimb;
    }else if (dblSpdX > 0 && (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1)){
      imgTempPlayer = imgPlayerRight;
    } else if (dblSpdX < 0 && (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1)){
      imgTempPlayer = imgPlayerLeft;    
    } else if(dblSpdX > 0){
      imgTempPlayer = imgPlayerRightJump;
    } else if(dblSpdX < 0){
      imgTempPlayer = imgPlayerLeftJump;
    } else {
      imgTempPlayer = imgPlayerFor;
    }
    image(imgTempPlayer, intX, intY - 44);
  }




  public void jumpMechanic(){
    if (blnJump && blnJumpCd && (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1)) {
      dblSpdY = -16;
      dblSpdX *= 1.4;
      blnJumpCd = false;
      intDashing = -1;
      blnGravity = true;
    } else if (blnJump && blnJumpCd && ((detect(intX - 2, intY) == 1) || (detect(intX - 2, intY - 44) == 1))) {
      dblSpdX = 16;
      if (intDashing > -1 && dblSpdY < 0){
        dblSpdY = 16 * (dblSpdY / 40 - 1.);
      } else {
        dblSpdY = -16;
      }
      intMoveDisable = -12;
      blnJumpCd = false;
      intDashing = -1;
      blnGravity = true;
    } else if (blnJump && blnJumpCd && ((detect(intX + 46, intY) == 1) || (detect(intX + 46, intY - 44) == 1))) {
      dblSpdX = -16;
      if (intDashing > -1 && dblSpdY < 0){
        dblSpdY = 16 * (dblSpdY / 40 - 1.);
      } else {
        dblSpdY = -16;
      }
      intMoveDisable = 12;
      blnJumpCd = false;
      intDashing = -1;
      blnGravity = true;
    }
  }




  /**
   * shakes screen when dashing
   */
  public void screenShake(){
    if (intDashing > 8) {
      translate( 5, 5);
    } else if (intDashing > 6) {
      translate(-5, -5);
    } else if (intDashing > 4) {
      translate(5, 5);
    }
  }
  /* checks user input and saves it to boolean values
   */
  public void keyPressed() {
    if (keyCode == LEFT) {
      blnLeft = true;
    }
    if (keyCode == RIGHT) {
      blnRight = true;
    }
    if (keyCode == DOWN) {
      blnDown = true;
    }
    if (keyCode == UP) {
      blnUp = true;
    }




    if (key == 'Z' || key == 'z') {
      blnClimb = true;
    }
    if (key == 'X' || key == 'x') {
      blnDash = true;
    }
    if (key == 'C' || key == 'c') {
      blnJump = true;
    }
   
  }




  public void keyReleased() {
    if (keyCode == LEFT) {
      blnLeft = false;
    }
    if (keyCode == RIGHT) {
      blnRight = false;
    }
    if (keyCode == DOWN) {
      blnDown = false;
    }
    if (keyCode == UP) {
      blnUp = false;
    }




    if (key == 'Z' || key == 'z') {
      blnClimb = false;
    }
    if (key == 'X' || key == 'x') {
      blnDash = false;
      blnDashCd = true;
    }
    if (key == 'C' || key == 'c') {
      blnJump = false;
      blnJumpCd = true;
    }
  }




  public void dashMechanic(){
    if (blnDash && intDashs != 0 && blnDashCd) {
      if (blnUp && blnRight) {
        dblSpdX = 15;
        dblSpdY = -15;
      } else if (blnUp && blnLeft) {
        dblSpdX = -15;
        dblSpdY = -15;
      } else if (blnDown && blnLeft) {
        dblSpdX = -15;
        dblSpdY = 15;
      } else if (blnDown && blnRight) {
        dblSpdX = 15;
        dblSpdY = 15;
      } else if (blnUp) {
        dblSpdX = 0;
        dblSpdY = -20.8;
      } else if (blnLeft) {
        dblSpdX = -20.8;
        dblSpdY = 0;
      } else if (blnDown) {
        dblSpdX = 0;
        dblSpdY = 20.8;
      } else {
        dblSpdX = 20.8;
        dblSpdY = 0;
      }
      blnGravity = false;
      intDashs--;
      intDashing = 11;
      blnDashCd = false;
    }
  }
  public void walking(){
    if (intDashing == -1) {
      if (blnClimb && dblStamina > 0 && (detect(intX - 1, intY) == 1 || detect(intX + 45, intY) == 1 || detect(intX - 1, intY - 44) == 1 || detect(intX + 45, intY - 44) == 1)) {
        dblSpdX = 0;
        dblSpdY = 0;
        if (blnUp) {
          dblSpdY = -2.75;
          dblStamina -= 1;
        } else if (blnDown) {
          dblSpdY = 2.75;
          dblStamina -= 1;
        }
      } else if (blnRight && dblSpdX > 7.5 && dblSpdX < 10 && intMoveDisable <= 0) {
        dblSpdX = 10;
      } else if (blnLeft && dblSpdX < -7.5 && dblSpdX > -10 && intMoveDisable >= 0) {
        dblSpdX = -10;
      } else if (blnRight && dblSpdX < 10 && intMoveDisable <= 0) {
        dblSpdX += 2.5;
      } else if (blnLeft && dblSpdX > -10 && intMoveDisable >= 0) {
        dblSpdX -= 2.5;
      } else if (blnRight && dblSpdX > 10 && intMoveDisable <= 0 && (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1)) {
        dblSpdX -= 1.5;
      } else if (blnLeft && dblSpdX < -10 && intMoveDisable >= 0 && (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1)) {
        dblSpdX += 1.5;
      } else if (blnRight && dblSpdX > 10 && intMoveDisable <= 0) {
        dblSpdX -= 0.75;
      } else if (blnLeft && dblSpdX < -10 && intMoveDisable >= 0) {
        dblSpdX += 0.75;
      } else if (dblSpdX > -2 && dblSpdX < 2) {
        dblSpdX = 0;
      } else if (dblSpdX < 0) {
        dblSpdX += 1.5;
      } else if (dblSpdX > 0) {
        dblSpdX -= 1.5;
      }
     
    } else if (intDashing == 0) {
      blnGravity = true;
      intDashing--;
      dblSpdX *= (2./3);
      dblSpdY *= (2./3);




    } else {
      intDashing--;
    }
  }
 
  /*
   * updates the location of player
   * @param spd X
   * @param spd Y
   *
   * @author George
   */
  public void positionUpdate() {
    if (detect(intX, intY) == -1 || detect(intX, intY - 44) == -1 || detect(intX + 44, intY) == -1 || detect(intX +44, intY - 44) == -1){
      intDeathCount++;
      dblDeathTime = 10.5;
    } else if (detect(intX, intY) == 2 || detect(intX, intY - 44) == 2 || detect(intX + 44, intY) == 2 || detect(intX +44, intY - 44) == 2){// and
      blnNewLvl = true;      
      if (intStage == 4){
        blnEnd = true;
      } else {
        intStage++;
        respawn();
      }
     
    } else if (detect(intX, intY) == -2 || detect(intX, intY - 44) == -2 || detect(intX + 44, intY) == -2 || detect(intX +44, intY - 44) == -2){ // and
      blnNewLvl = false;
      intStage--;
      respawn();
    } else  {
      if (detect(intX, intY) == 3 || detect(intX, intY - 44) == 3 || detect(intX + 44, intY) == 3 || detect(intX +44, intY - 44) == 3){
        blnKey = true;
      }
      int intCounter = 0;
      if (dblSpdX >= 45) {
        dblSpdX = 44.5;
      } else if (dblSpdX <= -45) {
        dblSpdX = -44.5;
      }
      if (dblSpdY >= 45) {
        dblSpdY = 44.5;
      } else if (dblSpdY <= -45) {
        dblSpdY = -44.5;
      }
      if (detect(intX, intY) == -2 || detect(intX, intY - 44) == -2 || detect(intX + 44, intY) == -2 || detect(intX +44, intY - 44) == -2){
       
      }
      // bottom detection
      if (intY - 44 + dblSpdY >= 0 && (detect(intX, intY + (int) dblSpdY) == 1 || detect(intX + 44, intY + (int) dblSpdY) == 1)) {
        while (detect(intX, intY + intCounter) != 1 && detect(intX + 44, intY + intCounter) != 1) {
          intCounter++;
        }
        intY += intCounter - 1;
        if (intDashing == -1) {
          dblSpdY = 0;
        }




      } else if (intY - 44 + dblSpdY >= 0 && (detect(intX, intY - 44 + (int) dblSpdY) == 1 || detect(intX + 44, intY - 44 + (int) dblSpdY) == 1)) { // top detection
        while (detect(intX, intY - 44 - intCounter) != 1 && detect(intX + 44, intY - 44 - intCounter) != 1) {
          intCounter++;
        }
        intY += (-intCounter + 1);
        if (intDashing == -1) {
          dblSpdY = 0;
        }
      } else {
        if (intY + dblSpdY - 44 >= 0){
          intY += (int) dblSpdY;
        }
      }




      // left detection
      intCounter = 0;
      if (detect(intX + (int) dblSpdX, intY) == 1 || detect(intX + (int) dblSpdX, intY - 44) == 1) {
        while (detect(intX - intCounter, intY) != 1 && detect(intX - intCounter, intY - 44) != 1) {
          intCounter++;
        }
        intX += (-intCounter + 1);
        if (intDashing == -1) {
          dblSpdX = 0;
        }




      } else if (detect(intX + (int) dblSpdX + 44, intY) == 1 || detect(intX + (int) dblSpdX + 44, intY - 44) == 1) { // right detection
        while (detect(intX + intCounter + 44, intY) != 1 && detect(intX + intCounter + 44, intY - 44) != 1) {
          intCounter++;
        }
        intX += (intCounter - 1);
        if (intDashing == -1) {
          dblSpdX = 0;
        }
      } else {
        if (intX + dblSpdX + 44 < 1440 && intX + dblSpdX > 0){
          intX += (int) dblSpdX;
        }
      }
      intPastPos[intGameTick % 12][0] = intX;
      intPastPos[intGameTick % 12][1] = intY;
    }
  }




  public int detect(int intXCoordinate, int intYCoordinate) {
    return intHitMap[intStage][intXCoordinate / 45][intYCoordinate / 45];
  }




  public void visualizeGrid() {
    stroke((int) (dblStamina / 125 * 200 + 50));
    for (int i = 0; i < width; i += 45) {
      for (int j = 0; j < height; j += 45) {
        if (detect(i, j) == 1) {
          fill(0);
          rect(i, j, 45, 45);
        }else if (detect(i, j) == -1){
          fill(255, 0, 0);
          rect(i, j, 45, 45);
        }
      }
    }
  }




  public void stage0(){
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 20; j++) {
        intHitMap[0][i][j] = 0;
        if (i == 31){
          intHitMap[0][i][j] = 2;
        } else if (j == 19){
          intHitMap[0][i][j] = -1;
        }
      }
    }
    for (int i =1; i < 12; i++) {
        intHitMap[0][i][18] = 1;
    }
    for (int i = 15; i < 21; i++) {
      for (int j = 15; j < 20; j++) {
        intHitMap[0][i][j] = 1;
      }
    }
    for (int i = 24; i < 32; i++) {
      for (int j = 14; j < 20; j++) {
        intHitMap[0][i][j] = 1;
      }
    }
    for (int i = 6; i < 12; i++) {
      for (int j = 15; j < 18; j++) {
        intHitMap[0][i][j] = 1;
      }
    }
    intHitMap[0][12][15] = 1;
    intHitMap[0][23][14] = 1;
    for (int i = 0; i <= 31; i++) {
      intHitMap[0][i][20] = -1;
    }
  }
  public void stage1() {
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 20; j++) {
        intHitMap[1][i][j] = 0;
      }
    }
    for (int i = 0; i <= 20; i++) {
      intHitMap[1][32][i] = 1;
    }
    for (int i = 0; i <= 5; i++) {
      for (int j = 14; j <= 19; j++) {
        intHitMap[1][i][j] = 1;
      }
    }
    for (int i = 30; i <= 31; i++) {
      for (int j = 11; j <= 19; j++) {
        intHitMap[1][i][j] = 1;
      }
    }
    for (int i = 18; i <= 24; i++) {
      for (int j = 17; j <= 19; j++) {
        intHitMap[1][i][j] = 1;
      }
    }
    for (int i = 0; i <= 10; i++) {
      intHitMap[1][i][7] = 1;
    }
    for (int i = 19; i <= 25; i++) {
      intHitMap[1][i][8] = 1;
    }
    for (int i = 0; i <= 6; i++) {
      intHitMap[1][0][i] = 2;
    }
    for (int i = 8; i <= 13; i++) {
      intHitMap[1][0][i] = -2;
    }
    intHitMap[1][6][14] = 1;
    intHitMap[1][25][17] = 1;
    for (int i = 0; i <= 31; i++) {
      intHitMap[1][i][20] = -1;
    }
  }
  public void stage2(){    
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 20; j++) {
        intHitMap[2][i][j] = 0;
      }
    }
    for (int i = 17; i <= 18; i++) {
      for (int j = 6; j <= 19; j++) {
        intHitMap[2][i][j] = 1;
      }
    }
    for (int i = 23; i <= 24; i++) {
      for (int j = 0; j <= 15; j++) {
        intHitMap[2][i][j] = 1;
      }
    }
    for (int i = 6; i <= 7; i++) {
      for (int j = 8; j <= 14; j++) {
        intHitMap[2][i][j] = 1;
      }
    }
    for (int i = 0; i <= 5; i++) {
      for (int j = 14; j <= 15; j++) {
        intHitMap[2][i][j] = 1;
      }
    }
    for (int i = 2; i <= 3; i++) {
      for (int j = 10; j <= 12; j++) {
        intHitMap[2][i][j] = 3;
      }
    }
    for (int i = 16; i <= 19; i++) {
      intHitMap[2][0][i] = 1;
    }
    intHitMap[0][1][16] = 1;
    for (int i = 4; i <= 9; i++) {
      intHitMap[2][i][19] = 1;
    }
    for (int i = 10; i <= 13; i++) {
      intHitMap[2][i][9] = 1;
    }
    for (int i = 22; i <= 25; i++) {
      intHitMap[2][i][19] = 1;
    }
    for (int i = 27; i <= 31; i++) {
      intHitMap[2][i][9] = 1;
    }
    for (int i = 8; i <= 19; i++) {
      intHitMap[2][31][i] = 1;
    }
    for (int i = 14; i <= 19; i++) {
      intHitMap[2][30][i] = 1;
    }
    for (int i = 1; i <= 3; i++) {
      intHitMap[2][i][19] = 2;
    }
    for (int i = 0; i <= 7; i++) {
      intHitMap[2][31][i] = -2;
    }
    for (int i = 0; i <= 31; i++) {
      intHitMap[2][i][20] = -1;
    }
    for (int i = 0; i <= 20; i++) {
      intHitMap[2][32][i] = 1;
    }
    intHitMap[2][29][14] = 1;
    intHitMap[2][16][6] = 1;
    intHitMap[2][5][13] = -1;
    intHitMap[2][6][7] = -1;
    intHitMap[2][7][7] = -1;
    intHitMap[2][8][12] = -1;
    intHitMap[2][8][13] = -1;
    intHitMap[2][17][1] = -1;
    intHitMap[2][18][1] = -1;
    intHitMap[2][17][5] = -1;
    intHitMap[2][18][5] = -1;




    intHitMap[2][19][10] = -1;
    intHitMap[2][19][14] = -1;




    intHitMap[2][19][18] = -1;
    intHitMap[2][19][19] = -1;
    intHitMap[2][22][8] = -1;
    intHitMap[2][22][12] = -1;
  }
  public void stage3(){
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 20; j++) {
        intHitMap[3][i][j] = 0;
      }
    }
    for (int i = 0; i <= 20; i++) {
      intHitMap[3][32][i] = 1;
    }
    for (int i = 4; i <= 8; i++) {
      for (int j = 0; j <= 3; j++) {
        intHitMap[3][i][j] = 1;
      }
    }
    for (int i = 0; i <= 2; i++) {
      for (int j = 9; j <= 19; j++) {
        intHitMap[3][i][j] = 1;
      }
    }
    for (int i = 10; i <= 19; i++) {
      for (int j = 11; j <= 12; j++) {
        intHitMap[3][i][j] = 1;
      }
    }
    for (int i = 15; i <= 16; i++) {
      for (int j = 4; j <= 10; j++) {
        intHitMap[3][i][j] = 1;
      }
    }
    for (int i = 13; i <= 18; i++) {
      for (int j = 17; j <= 19; j++) {
        intHitMap[3][i][j] = 1;
      }
    }
    for (int i = 24; i <= 29; i++) {
      for (int j = 9; j <= 19; j++) {
        intHitMap[3][i][j] = 1;
      }
    }
    for (int i = 0; i <= 3; i++) {
      intHitMap[3][0][i] = 1;
    }
    for (int i = 11; i <= 14; i++) {
      intHitMap[3][i][10] = 1;
    }
    for (int i = 5; i <= 9; i++) {
      intHitMap[3][i][18] = 1;
    }
    for (int i = 6; i <= 8; i++) {
      intHitMap[3][i][19] = 1;
    }
    for (int i = 16; i <= 18; i++) {
      intHitMap[3][i][16] = 1;
    }
    for (int i = 0; i <= 4; i++) {
      intHitMap[3][29][i] = 1;
    }
    for (int i = 0; i <= 3; i++) {
      intHitMap[3][31][i] = 2;
    }
    intHitMap[3][30][4] = 1;
    intHitMap[3][31][4] = 1;
    for (int i = 11; i <= 14; i++) {
      intHitMap[3][i][9] = -1;
    }
    for (int i = 4; i <= 8; i++) {
      intHitMap[3][14][i] = -1;
    }
    for (int i = 9; i <= 11; i++) {
      intHitMap[3][23][i] = -1;
    }
    intHitMap[3][3][9] = 1;
    intHitMap[3][5][17] = -1;
    intHitMap[3][9][17] = -1;
    intHitMap[3][10][10] = -1;
    intHitMap[3][15][3] = -1;
    intHitMap[3][16][3] = -1;
    intHitMap[3][17][10] = -1;
    intHitMap[3][15][16] = -1;
    intHitMap[3][24][8] = -1;
    intHitMap[3][25][8] = -1;
  }
  public void stage4(){
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 20; j++) {
        intHitMap[4][i][j] = 0;
        if (j == 19) {
          intHitMap[4][i][j] = 1;
        }
      }
    }
    for (int i = 0; i <= 20; i++) {
      intHitMap[4][32][i] = 1;
    }
    for (int i = 9; i <= 12; i++) {
      for (int j = 9; j <= 18; j++) {
        intHitMap[4][i][j] = 1;
      }
    }
    for (int i = 19; i <= 22; i++) {
      for (int j = 0; j <= 13; j++) {
        intHitMap[4][i][j] = 1;
      }
    }
    for (int i = 28; i <= 31; i++) {
      for (int j = 3; j <= 10; j++) {
        intHitMap[4][i][j] = 1;
      }
    }
    for (int i = 28; i <= 31; i++) {
      for (int j = 15; j <= 19; j++) {
        intHitMap[4][i][j] = 1;
      }
    }
    for (int i = 29; i <= 31; i++) {
      for (int j = 0; j <= 2; j++) {
        intHitMap[4][i][j] = 2;
      }
    }
    for (int i = 11; i <= 18; i++) {
      intHitMap[4][0][i] = -2;
    }
    for (int i = 0; i <= 5; i++) {
      intHitMap[4][i][10] = 1;
    }
    for (int i = 4; i <= 13; i++) {
      intHitMap[4][18][i] = -1;
    }
    for (int i = 19; i <= 22; i++) {
      intHitMap[4][i][14] = -1;
    }
    for (int i = 19; i <= 22; i++) {
      intHitMap[4][i][17] = -1;
    }
    for (int i = 19; i <= 22; i++) {
      intHitMap[4][i][18] = 1;
    }
    for (int i = 6; i <= 15; i++) {
      intHitMap[4][i][18] = -1;
    }
    for (int i = 5; i <= 7; i++) {
      intHitMap[4][27][i] = -1;
    }
    intHitMap[4][6][10] = -1;
    intHitMap[4][9][8] = -1;
    intHitMap[4][10][8] = -1;
    intHitMap[4][23][2] = -1;
    intHitMap[4][23][3] = -1;
    intHitMap[4][23][8] = -1;
    intHitMap[4][24][10] = -1;
    intHitMap[4][23][11] = -1;
    intHitMap[4][27][18] = -1;
    intHitMap[4][23][9] = 1;
    intHitMap[4][24][9] = 1;
    intHitMap[4][23][10] = 1;
  }
  public void mouseClicked(){
    if (blnTitle){
      blnGravity = true;
    }
    blnTitle = false;
  }
}