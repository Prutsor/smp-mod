package nl.prutsor.smp;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.prutsor.pruttel.api;
import nl.prutsor.pruttel.api.Application;

import com.google.gson.Gson;

import java.util.Map;

@Mod("smp")
public class mod
{
    private static final Logger LOGGER = LogManager.getLogger();

    public mod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        Gson gson = new Gson();

        String request = api.get("/api/applications.php?type=view&client_id=smp");
        Application application = gson.fromJson(request, Application.class);

        String rawData = application.data.replaceAll("\\\\", "");

        Map<String, String> data = new Gson().fromJson(rawData, Map.class);

        ServerList list = new ServerList(Minecraft.getInstance());
        ServerData server = new ServerData("SMP", data.get("ip"), false);

        int listSize = list.size();

        if (listSize == 0)
        {
            list.add(server);
        }
        else
        {
            list.replace(0, server);
        }

        list.save();
    }
}
