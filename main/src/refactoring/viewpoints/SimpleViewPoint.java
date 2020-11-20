package refactoring.viewpoints;

import refactoring.PointOfView;

public class SimpleViewPoint implements PointOfView {
    Heading head;
    Position pos;

    public SimpleViewPoint(Heading head, Position pos) {
        this.head = head;
        this.pos = pos;
    }
    public SimpleViewPoint(String head, Position pos) {
        this.head = Heading.of(head);
        this.pos = pos;
    }

    @Override
    public void turnLeft() {
        head.turnLeft();
    }

    @Override
    public void turnRight() {
        head.turnRight();
    }

    @Override
    public void forward() {
        pos.forward(head);
    }

    @Override
    public void backward() {
        pos.backward(head);
    }

    public static class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position forward(Heading heading) {
            if(isYAxis(heading)) return new Position(this.x, this.y - heading.ordinal() + 1);
            else return new Position(this.x - heading.ordinal() + 2, this.y);
        }

        public Position backward(Heading heading) {
            if(isYAxis(heading)) return new Position(this.x, this.y + heading.ordinal() - 1);
            else return new Position(this.x + heading.ordinal() - 2, this.y);
        }

        private boolean isYAxis(Heading heading) {
            return heading.ordinal() % 2==0;
        }

        @Override
        public boolean equals(Object object) {
            return isSameClass(object) && equals((Position) object);
        }

        private boolean equals(Position position) {
            return position == this || (x == position.x && y == position.y);
        }

        private boolean isSameClass(Object object) {
            return object != null && object.getClass() == Position.class;
        }

    }


    public enum Heading {
        North, East, South, West;

        public static Heading of(String label) {
            return of(label.charAt(0));
        }

        public static Heading of(char label) {
            if (label == 'N') return North;
            if (label == 'S') return South;
            if (label == 'W') return West;
            if (label == 'E') return East;
            return null;
        }

        public Heading turnRight() { return values()[add(+1)]; }

        public Heading turnLeft() {	return values()[add(-1)]; }

        private int add(int offset) { return (this.ordinal() + offset + values().length) % values().length; }

    }
}
