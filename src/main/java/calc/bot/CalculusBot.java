package calc.bot;

/** Main Class; Creates Calculus bot object and by doing so starts listeners
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */
public class CalculusBot {

        public static void main(String[] args) {

            String discordToken = "PUT_YOUR_TOKEN_HERE";

            CalculusListener calculus = new CalculusListener(discordToken);

        }
}