/*
 * Copyright 2015-2016 inventivetalent. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and contributors and should not be interpreted as representing official policies,
 *  either expressed or implied, of anybody else.
 */

package org.inventivetalent.mapmanager.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.mapmanager.MapManagerPlugin;
import org.inventivetalent.mapmanager.manager.MapManager;
import org.inventivetalent.mapmanager.wrapper.MapWrapper;

/**
 * Event called when a client sends a CreativeInventoryUpdate-Packet for a {@link MapManager} map
 * (usually after using {@link org.inventivetalent.mapmanager.controller.MapController#showInInventory(Player, int, boolean)})
 * <p>
 * Cancelled by default.
 */
public class CreativeInventoryMapUpdateEvent extends Event implements Cancellable {

	private Player    player;
	private int       slot;
	private ItemStack itemStack;

	private MapWrapper mapWrapper;

	private boolean cancelled = true;

	public CreativeInventoryMapUpdateEvent(Player player, int slot, ItemStack itemStack) {
		this.player = player;
		this.slot = slot;
		this.itemStack = itemStack;
	}

	/**
	 * @return the {@link Player} that sent the update
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the update item slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @return the updated {@link ItemStack}
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * @return the {@link MapWrapper} of the item
	 */
	public MapWrapper getMapWrapper() {
		if (this.mapWrapper != null) { return this.mapWrapper; }
		if (getItemStack() == null) { return null; }
		if (getItemStack().getType() != Material.MAP) { return null; }
		MapManager mapManager = ((MapManagerPlugin) Bukkit.getPluginManager().getPlugin("MapManager")).getMapManager();
		return this.mapWrapper = mapManager.getWrapperForId(getPlayer(), getItemStack().getDurability());
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}

	private static HandlerList handlerList = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}
}
