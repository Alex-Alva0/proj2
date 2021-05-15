import java.util.*;
// push, pop, top, and isEmpty
public class Mystack {
    protected java.util.List stack = new ArrayList();
    int top = -1;
    public void push(Card card){
        top +=1;
        stack[top()] = card;
    }
    public Card pop(){
        if(isEmpty()){
            return null;
        }
        else{
            return stack[top()];
            top-=1;
        }
    }
    public void top(){
        return top;
    }
    public void isEmpty(){
        if(top()<0){return true;}
        else{return false;}
    }
}