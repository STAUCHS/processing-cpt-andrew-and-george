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
  boolean blnDash;
  boolean blnDashCd;
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
  boolean blnGravity = true;
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

  

  int[][][] intHitMap = new int[2][32][20];
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
    frameRate(50);
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
    
  }

  public void draw() {
    screenShake();
    // Updates background
    background(120, 210, 255);

    visualizeGrid();
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
    } else if((int)dblDeathTime == 0){
      intX = 100;
      intY = 600;
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

  public void pause(long lngStart, long lngDuration){
    while (System.nanoTime() - lngStart < lngDuration){
      //null
    }
  }

  public void drawPlayer(){
    // fill((int) (255 - dblStamina / 125 * 200));
    // stroke((int) (255 - dblStamina / 125 * 200));
    
    // rect(intPastPos[(intGameTick + 1) % 12][0] + 20, intPastPos[(intGameTick + 1) % 12][1] - 30, 10, -10);
    // rect(intPastPos[(intGameTick + 3) % 12][0] + 17, intPastPos[(intGameTick + 3) % 12][1] - 28, 14, -14);
    // rect(intPastPos[(intGameTick + 5) % 12][0] + 15, intPastPos[(intGameTick + 5) % 12][1] - 26, 18, -18);
    // rect(intPastPos[(intGameTick + 7) % 12][0] + 12, intPastPos[(intGameTick + 7) % 12][1] - 24, 22, -22);
    // rect(intPastPos[(intGameTick + 9) % 12][0] + 10, intPastPos[(intGameTick + 9) % 12][1] - 22, 26, -26);
    // rect(intPastPos[(intGameTick + 11) % 12][0] + 7, intPastPos[(intGameTick + 11) % 12][1] - 20, 30, -30);
    
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
    if (detect(intX, intY) == -1 || detect(intX, intY - 44) == -1 || detect(intX + 44, intY) == -1 || detect(intX +44, intY - 44)== -1){
      intDeathCount++;
      dblDeathTime = 10.5;
    }else if (detect(intX, intY) == 2 || detect(intX, intY - 44) == 2 || detect(intX + 44, intY) == 2 || detect(intX +44, intY - 44)== 2){
      intX = 45;
      intY = 675;
      intStage++;
    } else  {
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
      if (intY + dblSpdY - 44 >= 0 && intY + dblSpdY <= 900){
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
    if (intXCoordinate > 1440 || intXCoordinate < 0 || intYCoordinate > 900 || intYCoordinate < 0) {
      return 0;
    }
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
        if (i == 0){
          intHitMap[0][i][j] = 1;
        } else if (i == 31){
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
    intHitMap[0][21][15] = 1;
  }
  public void stage1() {
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 20; j++) {
        intHitMap[1][i][j] = 0;
        if (j > 18) {
          intHitMap[1][i][j] = 1;
        }
        if (i == 0 || i == 31) {
          intHitMap[1][i][j] = 1;
        }
      }
    }
    intHitMap[1][16][15] = 1;
    intHitMap[1][15][16] = 1;
    intHitMap[1][17][17] = -1;
    intHitMap[1][14][17] = 1;
    intHitMap[1][16][16] = 1;
    intHitMap[1][16][17] = 1;
    intHitMap[1][7][9] = 1;
    intHitMap[1][15][17] = 1;
  }

  
}