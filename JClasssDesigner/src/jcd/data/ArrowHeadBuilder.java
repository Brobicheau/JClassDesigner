/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

/**
 *
 * @author brobi
 */
public class ArrowHeadBuilder {
    static final String LINE = "LINE";
    static final String INTERFACE = "INTERFACE";
    static final String USES = "USES";
    static final String AGGREGATE = "AGGREGATE";    
    double triModArrowX;
    double triModNodeX;
    double triModArrowY;
    double triModNodeY;
    
    public ArrowHeadBuilder() {
        triModArrowX = 0;
        triModArrowY = 0;
        triModNodeX = 0;
        triModNodeY = 0;        

    }
        
    
    
    public Double[] buildArrowHead (String arrowHeadType, PositionState position) {
        Double[] dArray = null;
        if(null != position) switch (position) {
            case WEST:
                triModArrowX = 10.0;
                triModArrowY = 0.0;
                triModNodeX = 12.0;
                triModNodeY = 0.0;  
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           -6.6, 6.6,
                          -6.6, -6.6,
                          13.3, 0.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       -10.0, -10.0,
                      -20.0, 0.0,
                      -10.0, 10.0,
                      10.0, 0.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           -10.0, -10.0,
                          0.0, 0.0,
                          -10.0, 10.0,
                          10.0, 0.0};                    
                }
                break;
            case EAST:
                triModArrowX = -10.0;
                triModArrowY = 0.0;
                triModNodeX = -12.0;
                triModNodeY = 0.0;                
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           6.6, -6.6,
                          6.6, 6.6,
                          -13.3, 0.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       10.0, 10.0,
                      20.0, 0.0,
                      10.0, -10.0,
                      -10.0, 0.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           10.0, 10.0,
                          0.0, 0.0,
                          10.0, -10.0,
                          -10.0, 0.0};                    
                }
                break;
            case SOUTH:
                triModArrowX = 0.0;
                triModArrowY = 10.0;
                triModNodeX = 0.0;
                triModNodeY = 15.0;  
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           10.0, -10.0,
                          -10.0, -10.0,
                          0.0, 20.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       -10.0, -10.0,
                      0.0, -20.0,
                      10.0, -10.0,
                        0.0, 10.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           -10.0, -10.0,
                          0.0, 0.0,
                          10.0, -10.0,
                          0.0, 10.0};                    
                }
                break;
            case NORTH:
                triModArrowX = 0.0;
                triModArrowY = -10.0;
                triModNodeX = 0.0;
                triModNodeY = -15.0;  
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           -10.0, 10.0,
                          10.0, 10.0,
                          0.0, -20.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       10.0, 10.0,
                      0.0, 20.0,
                      -10.0, 10.0,
                        0.0, -10.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           10.0, 10.0,
                          0.0, 0.0,
                          -10.0, 10.0,
                          0.0, -10.0};                    
                }
                break;
            case NORTH_EAST:
                triModArrowX = 10.0;
                triModArrowY = 0.0;
                triModNodeX = 12.0;
                triModNodeY = 0.0;  
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           -6.6, 6.6,
                          -6.6, -6.6,
                          13.3, 0.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       -10.0, -10.0,
                      -20.0, 0.0,
                      -10.0, 10.0,
                      10.0, 0.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           -10.0, -10.0,
                          0.0, 0.0,
                          -10.0, 10.0,
                          10.0, 0.0};                    
                }
                break;
            case NORTH_WEST:
                triModArrowX = -10.0;
                triModArrowY = 0.0;
                triModNodeX = -12.0;
                triModNodeY = 0.0;                
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           6.6, -6.6,
                          6.6, 6.6,
                          -13.3, 0.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       10.0, 10.0,
                      20.0, 0.0,
                      10.0, -10.0,
                      -10.0, 0.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           10.0, 10.0,
                          0.0, 0.0,
                          10.0, -10.0,
                          -10.0, 0.0};                    
                }
                break;
            case SOUTH_WEST:
                triModArrowX = -10.0;
                triModArrowY = 0.0;
                triModNodeX = -12.0;
                triModNodeY = 0.0;                
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           6.6, -6.6,
                          6.6, 6.6,
                          -13.3, 0.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       10.0, 10.0,
                      20.0, 0.0,
                      10.0, -10.0,
                      -10.0, 0.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           10.0, 10.0,
                          0.0, 0.0,
                          10.0, -10.0,
                          -10.0, 0.0};                    
                }
                break;
            case SOUTH_EAST:
                triModArrowX = 10.0;
                triModArrowY = 0.0;
                triModNodeX = 12.0;
                triModNodeY = 0.0;  
                if(arrowHeadType.equals(INTERFACE)) {
                    dArray = new Double[] {
                           -6.6, 6.6,
                          -6.6, -6.6,
                          13.3, 0.0};
                }
                else if(arrowHeadType.equals(AGGREGATE)){
                dArray = new Double[] {
                       -10.0, -10.0,
                      -20.0, 0.0,
                      -10.0, 10.0,
                      10.0, 0.0};                    
                }
                else if(arrowHeadType.equals(USES)){
                    dArray = new Double[] {
                           -10.0, -10.0,
                          0.0, 0.0,
                          -10.0, 10.0,
                          10.0, 0.0};                    
                }
            default:
                break;
                
        }
        return dArray;
    }
    
    public double getTriModArrowX() {
        return triModArrowX;
    }
    
    public double getTriModArrowY() {
        return triModArrowY;
    }
    
    public double getTriModNodeX() {
        return triModNodeX;
    }
    
    public double getTriModNodeY() {
        return triModNodeY;
    }
}
