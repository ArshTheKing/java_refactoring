package refactoring;

import javafx.geometry.Pos;
import refactoring.viewpoints.SimpleViewPoint;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Rover {

    PointOfView view;
    private final Map<Order, Action> commands = new HashMap<>();

    {
        commands.put(Order.Forward, () -> view.forward());
        commands.put(Order.Backward, () -> view.backward());
        commands.put(Order.Right, () -> view.turnRight());
        commands.put(Order.Left, () -> view.turnLeft());
        commands.put(null, () -> view = view);
    }

    public Rover(PointOfView newView) {
        view = newView;
    }

    public PointOfView getView() {
        return view;
    }

    public void go(Order... orders) {
        go(Arrays.stream(orders));
    }

    public void go(String instructions) {
        go(Arrays.stream(instructions.split("")).map(Order::of));
    }

    public void go(Stream<Order> instructions) {
        instructions.forEach(order -> commands.get(order).execute());
    }


    public interface Action {
        void execute();
    }

    public enum Order {
        Forward, Backward, Left, Right;

        public static Order of(Character command) {
            if (command == 'L') return Left;
            if (command == 'R') return Right;
            if (command == 'F') return Forward;
            if (command == 'B') return Backward;
            return null;
        }

        public static Order of(String command) {
            return of(command.charAt(0));
        }

    }
}

