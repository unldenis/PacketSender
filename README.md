# PacketSender

PacketSender is an open-source Packet API. It allows to send packets with a similar functioning to ProtocolLib. Not ready for public use yet.
## Example usage
```java
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
      Player player = (Player) sender;
      Location location = player.getLocation();
      WrappedPacket wrappedPacket = new WrappedPacket("PacketPlayOutSpawnEntityLiving");
      final int entityType = 1;
      final int extraData = 1;
      final int entityID = new Random().nextInt();
      wrappedPacket.writeInt(0, entityID);
      wrappedPacket.writeInt(1, entityType);
      wrappedPacket.writeInt(2, extraData);

      wrappedPacket.writeUUID(0, UUID.randomUUID());

      wrappedPacket.writeDouble(0, location.getX());
      wrappedPacket.writeDouble(1, location.getY()/*+1.2*/);
      wrappedPacket.writeDouble(2, location.getZ());

      wrappedPacket.sendPacketSync(player);


      Bukkit.getScheduler().runTaskLaterAsynchronously(this, () ->{
          WrappedPacket packet = new WrappedPacket("PacketPlayOutEntityMetadata");
          packet.writeInt(0, entityID);

          WrappedDataWatcherObject dataWatcherObject = new WrappedDataWatcherObject(0, WrappedDataWatcherSerializer.BYTE);
          WrappedItem wrappedItem = new WrappedItem(dataWatcherObject, (byte) 0x20);
          packet.writeList(0, Collections.singletonList(wrappedItem.toNMS()));

          packet.sendPacketSync(player);
      }, 20L * 4);

      return true;
  }
```
