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

    public class Print implements IL {
        public String toString() {
            return String.format("invokestatic NubLib/print(I)V");
        }
    }
}
