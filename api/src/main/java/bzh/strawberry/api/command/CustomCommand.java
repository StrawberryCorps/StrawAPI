package bzh.strawberry.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
This file CustomCommand is part of a project StrawAPI.
It was created on 19/06/2020 at 22:41 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawAPI author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public abstract class CustomCommand implements CommandExecutor {

    private int power;
    private boolean consoleAllowed;

    public CustomCommand(int power, boolean console){
        this.power = power;
        this.consoleAllowed = console;
    }

    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args){
        if(!this.consoleAllowed && !(commandSender instanceof Player)){
            commandSender.sendMessage("[Command] This command can only be performed by a player.");
            return false;
        }
        return this.onCommand(commandSender, s, args);
    }

    protected abstract boolean onCommand(CommandSender commandSender, String s, String... args);


}
