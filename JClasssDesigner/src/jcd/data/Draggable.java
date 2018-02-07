/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author brobi
 */
public interface Draggable {
    public void drag(int x, int y);
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight);
    public String getType();
    public DesignerState getStartingState();

}