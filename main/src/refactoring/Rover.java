package refactoring;

import javafx.geometry.Pos;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Rover {

	private Heading heading;
	private Position position;
	private final Map<Order, Action> commands = new HashMap<>();

	{
		commands.put(Order.Forward, () -> this.position= this.position.forward(this.heading));
		commands.put(Order.Backward, () -> this.position= this.position.backward(this.heading));
		commands.put(Order.Right, () -> this.heading=this.heading.turnRight());
		commands.put(Order.Left, () -> this.heading= this.heading.turnLeft());
		commands.put(null,() -> this.heading=this.heading);
	}

	public Rover(String facing, int x, int y) {
		this.heading=Heading.of(facing);
		this.position = new Position(x, y);
	}

	public Rover(Heading heading, int x, int y) {
		this.heading=heading;
		this.position = new Position(x, y);
	}

	public Rover(Heading heading, Position startingPosition) {
		this.heading=heading;
		this.position = startingPosition;
	}

	public Heading heading() {
		return this.heading;
	}

	public Position position() {
		return this.position;
	}

	public void go(Order ... orders){
		go(Arrays.stream(orders));
	}

	public void go(String instructions) {
		go(Arrays.stream(instructions.split("")).map(Order::of));
	}
	public void go(Stream<Order> instructions) {
		instructions.forEach(order -> commands.get(order).execute());
	}


	public interface Action{
		void execute();
	}

	public enum Order{
		Forward,Backward,Left,Right;
		public static Order of(Character command){
			if(command=='L') return Left;
			if(command=='R') return Right;
			if(command=='F') return Forward;
			if(command=='B') return Backward;
			return null;
		}
		public static Order of(String command){
			return of(command.charAt(0));
		}

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

