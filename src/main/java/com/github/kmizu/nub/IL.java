package com.github.kmizu.nub;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by nomaddo on 2017/01/04.
 */
public interface IL {
    public abstract String toString();

    public class Iadd implements IL {
        public String toString(){
            return "iadd";
        }
    };
    public class Isub implements IL {
        public String toString(){
            return "isub";
        }
    };

    public class Imul implements IL {
        public String toString(){
            return "imul";
        }
    }

    public class Idiv implements IL {
        public String toString(){
            return "idiv";
        }
    }

    public class Ldc implements IL {
        public int op1;
        public Ldc (int c){
            this.op1 = c;
        }

        public String toString(){
            return String.format("ldc %d", this.op1);
        }
    }

    public class Iload implements IL {
        public int op1;
        public Iload (int c){
            this.op1 = c;
        }

        public String toString(){
            return String.format("iload %d", this.op1);
        }
    }

    public class Istore implements IL {
        public int op1;
        public Istore (int c) {
            this.op1 = c;
        }

        public String toString() {
            return String.format("istore %d", this.op1);
        }
    }

    public class Goto implements IL {
        public String op1;

        public String toString() {
            return String.format("goto %s", this.op1);
        }

        public Goto (String label){
            this.op1 = label;
        }
    }

//   virtual instructions from here
    public class Print implements IL {
        public String toString() {
            return String.format("invokestatic NubLib/print(I)V");
        }
    }

    public class Label implements IL {
        public String op1;

        public String toString(){
            return String.format("%s:", this.op1);
        }

        public Label(String label) {
            this.op1 = label;
        }
    }

    public class If_icmpeq implements IL {
        public String op1;

        public If_icmpeq (String op1) {
            this.op1 = op1;
        }

        public String toString() {
            return String.format("if_icmpeq %s", this.op1);
        }
    }

    public class If_icmpge implements IL {
        public String op1;

        public If_icmpge (String op1) {
            this.op1 = op1;
        }

        public String toString() {
            return String.format("if_icmpge %s", this.op1);
        }
    }


    public class If_icmple implements IL {
        public String op1;

        public If_icmple (String op1) {
            this.op1 = op1;
        }

        public String toString() {
            return String.format("if_icmple %s", this.op1);
        }
    }


    public class If_icmpgt implements IL {
        public String op1;

        public If_icmpgt (String op1) {
            this.op1 = op1;
        }

        public String toString() {
            return String.format("if_icmpgt %s", this.op1);
        }
    }

    public class If_icmplt implements IL {
        public String op1;

        public If_icmplt (String op1) {
            this.op1 = op1;
        }

        public String toString() {
            return String.format("if_icmplt %s", this.op1);
        }


    }
}
