//implementing echo Command (Output parameters)
public class EchoCommand extends Command {

    public EchoCommand(String[] arguments) {
        this.arguments = arguments;
    }

    public EchoCommand(IOData data) {
        this.data = data;
    }

    public IOData execute() {
        if (arguments != null)
        {
            return new IOData(arguments);
        }
        else
        {
            return data;
        }
    }

}
