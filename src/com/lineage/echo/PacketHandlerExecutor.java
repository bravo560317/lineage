package com.lineage.echo;

public abstract class PacketHandlerExecutor extends OpcodesClient
{
    public abstract void handlePacket(byte[] p0);
}
