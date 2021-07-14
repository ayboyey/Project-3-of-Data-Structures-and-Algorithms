package lab03;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Document{
    public String name;
    public TwoWayLinkedListWithHead<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name;
        link=new TwoWayLinkedListWithHead<Link>();
        load(scan);
    }
    public void load(Scanner scan) {
        StringBuffer document = new StringBuffer("Document: " + this.name);
 
        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (line.equals("eod")) break;
            document.append(line).append('\n');
        }
 
        Pattern pattern = Pattern.compile("link=[a-z]+\\w*_*\\w*");
        Matcher matcher = pattern.matcher(document.toString().toLowerCase());
 
        while (matcher.find()) {
            link.add(new Link(matcher.group().substring(5)));
        }
    }
    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    private static boolean correctLink( String link) {
        //TODO
        return true;
    }
 
    @Override
    public String toString() {
        String text = "Document: " + name;
 
        for (Link a : link) {
            if (a != null && a.ref != null) text += '\n' + a.ref;
        }
 
        return text;
    }
 
    public String toStringReverse() {
        String retStr="Document: "+name;
        return retStr+link.toStringReverse();
    }
 
}