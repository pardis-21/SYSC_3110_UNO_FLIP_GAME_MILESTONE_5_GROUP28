import java.util.*;
public class UNOCommandManager {
    private Deque<UNOCommand> undoLIFO;
    private Deque<UNOCommand> redoLIFO;
    private final int MAX_STACK_SIZE = 30;

    public UNOCommandManager(){
        undoLIFO = new ArrayDeque<>();
        redoLIFO = new ArrayDeque<>();
    }

    public void push(UNOCommand command){
        undoLIFO.push(command);
        redoLIFO.clear();

        //checking to see if the size of the stack is too big and if so clear the bottom of it
        while (undoLIFO.size() > MAX_STACK_SIZE){
            undoLIFO.removeLast();
        }
    }

    public boolean isUndoable(){
        if (undoLIFO.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isRedoable(){
        if (redoLIFO.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    public void undo(){
        if (isUndoable()){
            UNOCommand command = undoLIFO.pop();
            command.undoAction();
            redoLIFO.push(command);
        }
    }

    public void redo(){
        if (isRedoable()){
            UNOCommand command = redoLIFO.pop();
            command.redoAction();
            undoLIFO.push(command);
        }
    }

    public void clear(){
        undoLIFO.clear();
        redoLIFO.clear();
    }


}
