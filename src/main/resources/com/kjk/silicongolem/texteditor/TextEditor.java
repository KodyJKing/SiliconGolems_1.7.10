package com.kjk.silicongolem.texteditor;

import java.util.LinkedList;
import java.util.List;

import com.kjk.silicongolem.common.Common;

/*
 * Manages operations on text and a cursor in a regular grid.
 */
public class TextEditor {
	
	public TextEditor(String startText, int width){
		text = new StringBuilder(startText);
		textWidth = width;
		textChanged();
	}
	
	private int textWidth;
	
	private StringBuilder text;
	
	/*
	 * Line lengths are used to determine
	 * cursor x and y from the cursor
	 * position and the reverse calculation.
	 * They are recalculated when text changes.
	 */
	private List<Integer> lineLengths;
	private boolean lengthsDirty;
	
	/*
	 * Directly indexes the text
	 */
	private int cursor;
	
	/*
	 * Cursor x and y are mere
	 * functions of cursor.
	 * They are recalculated
	 * whenever the text or
	 * cursor change.
	 */
	private int cursorX, cursorY;
	private boolean cursorDirty;
	
	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = Common.clamp(cursor, 0, text.length());
		cursorDirty = true;
	}
	
	public int getCursorX() {
		updateCursor();
		return cursorX;
	}

	public int getCursorY() {
		updateCursor();
		return cursorY;
	}
	
	public void setCursorX(int x){
		updateCursor();
		cursorX = Common.clamp(x, 0, lineLengths.get(cursorY));
	}
	
	public void setCursorY(int y){
		updateCursor();
		cursorY = Common.clamp(y, 0, lineLengths.size() - 1);
		cursorX = Common.clamp(cursorX, 0, lineLengths.get(cursorY));
		cursor = charsUpto(cursorY) + cursorX;
		updateCursor();
	}
	
	public void moveCursor(int amount){
		setCursor(cursor + amount);
	}
	
	public void moveCursorY(int amount){
		updateCursor();
		setCursorY(cursorY + amount);
	}
	
	public void insert(int index, String s){
		text.insert(index, s);
		textChanged();
	}
	
	public void remove(int index){
		text.deleteCharAt(index);
		textChanged();
	}
	
	private void textChanged(){
		lengthsDirty = true;
		cursorDirty = true;
	}
	
	/*
	 * Called before accessing values
	 * which are functions of the
	 * cursor value.
	 */
	private void updateCursor(){
		if(!cursorDirty){
			return;
		}
		updateLineLenghts();
		
    	cursorX = 0;
    	cursorY = 0;
    	
    	int charsToGo = cursor;
    	
    	while(cursorY < lineLengths.size() && charsToGo > lineLengths.get(cursorY)){
    		charsToGo -= lineLengths.get(cursorY);
    		cursorY++;
    	}
    	
    	cursorX = charsToGo;
    	if(cursor > 0 && text.charAt(cursor - 1) == '\n'){
    		cursorY++;
    		cursorX = 0;
    	}
    	
    	cursorDirty = false;
    }
	
	/*
	 * Called before accessing values
	 * which are functions of the
	 * line lengths.
	 */
	
	private void updateLineLenghts(){
		if(!lengthsDirty){
			return;
		}
		
		lineLengths = new LinkedList<Integer>();
    	int length = 0;
    	for(int i = 0; i < text.length(); i++){
    		char c = text.charAt(i);
    		length++;
    		if(shouldBreak(i, length) || i + 1 == text.length()){
    			lineLengths.add(length);
    			length = 0;
    		}
    	}
    	
    	lengthsDirty = false;
	}
	
	private int charsUpto(int line){
		int result = 0;
		for(int i = 0; i < line; i++){
			result += lineLengths.get(i);
		}
		return result;
	}
	
	public boolean shouldBreak(int i, int length){
		return text.charAt(i) == '\n' || length + wordWidth(i) > this.textWidth;
	}
	
	private int wordWidth(int i){
		int count = 0;
		i++;
		while(i < text.length() && !isWhiteSpace(text.charAt(i))){
			count++;
			i++;
		}
		return count;
	}
	
	private boolean isWhiteSpace(char c){
		return "\n ".contains(Character.toString(c));
	}
	
	public String toString(){
		return text.toString();
	}
	
}
