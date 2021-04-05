package bzh.strawberry.api.command;

/*
 * This file AbstractCommand is part of a project StrawAPI.api.
 * It was created on 07/07/2020 00:09 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public abstract class AbstractCommand implements CommandExecutor {

    protected final Plugin plugin;
    protected final String permission;

    public AbstractCommand(Plugin plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp() && !commandSender.hasPermission(this.permission)) {
            commandSender.sendMessage("§cVous n'avez pas les droits nécessaires pour effectuer cette commande (REQUIRE_{permission})".replace("{permission}", permission.toUpperCase()));
            return false;
        }
        return onCommand(commandSender, s, strings);
    }

    protected abstract boolean onCommand(CommandSender sender, String label, String[] arguments);
}
