import processing.core.PApplet;

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
  int intDashs; // number of remaining dashes
  int intStage;
  double dblSpdX;
  double dblSpdY;
  double dblStamina;
  boolean blnGravity = true;
  // PImage player;

  int[][][] intHitMap = new int[1][32][20];

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
    stage1();

  }

  public void draw() {
    
    if (intDashing > 13) {
      translate(5, 5);
    } else if (intDashing > 11) {
      translate(-5, -5);
    } else if (intDashing > 9) {
      translate(5, 5);
    }
    // Updates background
    fill(120, 210, 255);
    rect(0, 0, width, height);
    visualizeGrid();

    if (dblSpdY < 15 && blnGravity) {
      dblSpdY += 1.4;
    }
    if (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1) {
      intDashs = 3;
      dblStamina = 125;
    }

    if (intDashing == -1) {
      if (blnClimb && dblStamina > 0 && (detect(intX - 1, intY) == 1 || detect(intX + 45, intY) == 1
          || detect(intX - 1, intY - 44) == 1 || detect(intX + 45, intY - 44) == 1)) {
        dblSpdX = 0;
        dblSpdY = 0;
        if (blnUp) {
          dblSpdY = -2.5;
          dblStamina -= 1;
        } else if (blnDown) {
          dblSpdY = 2.5;
          dblStamina -= 1;
        }
      } else if (blnRight && dblSpdX > 7.5 && dblSpdX < 10) {
        dblSpdX = 10;
      } else if (blnLeft && dblSpdX < -7.5 && dblSpdX > -10) {
        dblSpdX = -10;
      } else if (blnRight && dblSpdX < 10) {
        dblSpdX += 2.5;
      } else if (blnLeft && dblSpdX > -10) {
        dblSpdX -= 2.5;
      } else if (blnRight && dblSpdX > 10) {
        dblSpdX -= 0.5;
      } else if (blnLeft && dblSpdX < -10) {
        dblSpdX += 0.5;
      } else if (dblSpdX > -2 && dblSpdX < 2) {
        dblSpdX = 0;
      } else if (dblSpdX < 0) {
        dblSpdX += 1.5;
      } else if (dblSpdX > 0) {
        dblSpdX -= 1.5;
      }
      if (blnDash && intDashs != 0) {
        if (blnUp && blnRight) {
          dblSpdX = 12;
          dblSpdY = -12;
        } else if (blnUp && blnLeft) {
          dblSpdX = -12;
          dblSpdY = -12;
        } else if (blnDown && blnLeft) {
          dblSpdX = -12;
          dblSpdY = 12;
        } else if (blnDown && blnRight) {
          dblSpdX = 12;
          dblSpdY = 12;
        } else if (blnUp) {
          dblSpdX = 0;
          dblSpdY = -17;
        } else if (blnLeft) {
          dblSpdX = -17;
          dblSpdY = 0;
        } else if (blnDown) {
          dblSpdX = 0;
          dblSpdY = 17;
        } else {
          dblSpdX = 17;
          dblSpdY = 0;
        }
        blnGravity = false;
        intDashs--;
        intDashing = 15;
      }
    } else if (intDashing == 0) {
      for (int i = 0; i < 5000; i++) {
        // System.out.print(" ");
      }
      blnGravity = true;
      intDashing--;
      if (dblSpdY > 0) {
        dblSpdX = 0;
        dblSpdY = 7;
      } else {
        dblSpdX = 0;
        dblSpdY = 0;
      }
    } else {
      intDashing--;
    }
    if (blnJump && blnJumpCd && (detect(intX, intY + 1) == 1 || detect(intX + 44, intY + 1) == 1)) {
      dblSpdY = -16;
      blnJumpCd = false;
    } else if (blnJump && blnJumpCd && ((detect(intX - 2, intY) == 1) || (detect(intX - 2, intY - 44) == 1))) {
      dblSpdX = 25;
      dblSpdY = -16;
      blnJumpCd = false;
    } else if (blnJump && blnJumpCd && ((detect(intX + 46, intY) == 1) || (detect(intX + 46, intY - 44) == 1))) {
      dblSpdX = -25;
      dblSpdY = -16;
      blnJumpCd = false;
    }
    positionUpdate();
    fill((int) (dblStamina / 125 * 200 + 50));
    rect(intX, intY, 44, -44);
  }

  /*
   * @ checks user input and saves it to boolean values
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
    }
    if (key == 'C' || key == 'c') {
      blnJump = false;
      blnJumpCd = true;
    }
  }

  /*
   * updates the location of player
   * 
   * @param spd X
   * 
   * @param spd Y
   * 
   * @author George
   */
  public void positionUpdate() {
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
      intY += (int) dblSpdY;
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
      intX += (int) dblSpdX;
    }

  }

  public int detect(int intXCoordinate, int intYCoordinate) {
    if (intXCoordinate > 1440 || intXCoordinate < 0 || intYCoordinate > 900 || intYCoordinate < 0) {
      return 0;
    }
    return intHitMap[0][intXCoordinate / 45][intYCoordinate / 45];
  }

  public void visualizeGrid() {
    for (int i = 0; i < width; i += 45) {
      for (int j = 0; j < height; j += 45) {
        if (detect(i, j) == 1) {
          fill(0);
          rect(i, j, 45, 45);
        }
      }
    }
  }

  public void stage1() {
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 20; j++) {
        intHitMap[0][i][j] = 0;
        if (j >= 18) {
          intHitMap[0][i][j] = 1;
        }
        if (i == 0 || i == 31) {
          intHitMap[0][i][j] = 1;
        }
      }
    }
    intHitMap[0][16][15] = 1;
    intHitMap[0][15][16] = 1;
    intHitMap[0][17][17] = 1;
    intHitMap[0][14][17] = 1;
    intHitMap[0][16][16] = 1;
    intHitMap[0][16][17] = 1;
    intHitMap[0][7][9] = 1;
    intHitMap[0][15][17] = 1;
  }
}