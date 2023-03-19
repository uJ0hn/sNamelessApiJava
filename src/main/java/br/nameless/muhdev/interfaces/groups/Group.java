package br.nameless.muhdev.interfaces.groups;

public interface Group {

    boolean groupExists();

    String getName();

    String getColor();

    String getHtml();

    int getID();

    boolean isStaff();

    Group setName(String newn);

    Group setColor(String newc);

    Group setHtml(String newh);

    void execute();



}
