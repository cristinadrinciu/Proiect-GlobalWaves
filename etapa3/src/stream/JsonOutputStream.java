package stream;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonOutputStream {
    private static List<ObjectNode> commandOutputs = new ArrayList<>();

    public JsonOutputStream() {
    }

    /**
     * Adds a JSONPObject to the list of JSONPObjects
     * @param node the JSONPObject to add
     */
    public static void addJsonNode(ObjectNode node) {
        commandOutputs.add(node);
    }

    /**
     * Streams the list of JSONPObjects
     * @return the list of streams of JSONPObjects
     */
    public static Stream<ObjectNode> stream() {
        return commandOutputs.stream();
    }

    /**
     * @return the commandOutputs
     */
    public static List<ObjectNode> getCommandOutputs() {
        return commandOutputs;
    }

    /**
     * @param commandOutputs the commandOutputs to set
     */
    public static void setCommandOutputs(final List<ObjectNode> commandOutputs) {
        JsonOutputStream.commandOutputs = commandOutputs;
    }
}
