package cn.inrhor.imipet.pack

import cn.inrhor.imipet.ImiPet
import cn.inrhor.imipet.utlis.UnicodeUtil
import cn.mcres.imiPet.build.utils.SortComparator
import cn.mcres.imiPet.model.ModelInfoManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.regex.Pattern


class PackFile {

    fun run(file: File, modelInfo: ModelInfoManager) { // file 为 当前模型目录，为了读取pack.yml
        val packFile = file(file, "pack.yml") // 读取pack.yml
        if (!packFile.exists()) return
        val packYaml = yaml(packFile)
        if (!packYaml.getBoolean("autoSpawn")) return

        val modelFileName = file.name

        val packTargetFile = file(file, "pack")
        if (!packTargetFile.exists()) {
            TLocale.sendToConsole("PACK.NOT_EXIST_PACK_FILE", modelFileName)
            return
        }

        // 根目录纹理资源包
        val i = File.separator
        val defPath = "resourcePack" + i + "assets" + i + "minecraft"
        val pluginFile = ImiPet.loader.getPlugin().dataFolder

        val type = packYaml.getString("type")
        when (type) {
            "optiFine" -> {
                optiFine(pluginFile, defPath, modelFileName)
            }
            "customModelData" -> {
                cmd(pluginFile, defPath, modelFileName)
            }
            "all" -> {
                optiFine(pluginFile, defPath, modelFileName)
                cmd(pluginFile, defPath, modelFileName)
            }
            else -> return
        }

        /*
        json
        其json名称 要求 model.yml 和 skill.yml 状态/技能_动画ID.json
        生成于 resourcePack 文件夹
         */
        for (json in packTargetFile.listFiles()!!) {
            val jsonName = json.name
            if (!jsonName.endsWith(".json")) continue
            val state = jsonName.split(".")[0]
            // 截取 " " 之间字符
            when (type) {
                "optiFine" -> {
                    optiFineGenerate(pluginFile, defPath, modelFileName, i, state, modelInfo, json)
                }
                "customModelData" -> {
                    customModelDataGenerate(pluginFile, defPath, modelFileName, i, state, modelInfo, json)
                }
                "all" -> {
                    optiFineGenerate(pluginFile, defPath, modelFileName, i, state, modelInfo, json)
                    customModelDataGenerate(pluginFile, defPath, modelFileName, i, state, modelInfo, json)
                }
            }

            // 这就要求 图片名称 与 Json对应图片名称相同，并且仅一个图片
            val pngFile = file(packTargetFile, "$state.png")
            if (pngFile.exists()) {
                val jsonParser = JsonParser()

                val readerOver = FileReader(json)
                val jsonObject: JsonObject = jsonParser.parse(readerOver).asJsonObject
                readerOver.close()
                for (pngPath in jsonObject["textures"].asJsonObject.entrySet().iterator()) {

                    // 截取 " " 之间字符
                    val pattern = Pattern.compile("(?<=\").*?(?=\")")
                    val matcher = pattern.matcher(pngPath.toString())
                    while (matcher.find()) {
                        val pngPathGet = matcher.group()
                        if (pngPathGet.endsWith(state)) {
                            val getPngPath = pngPathGet.substring(0, pngPathGet.indexOf(state))
                            val resPackPngFile = file(pluginFile,
                                defPath + i +
                                        "textures"+ i +
                                        getPngPath
                            )
                            if (!resPackPngFile.exists()) {
                                resPackPngFile.parentFile.mkdirs()
                            }
                            FileUtils.copyFileToDirectory(pngFile, resPackPngFile)
                            val mcmetaFile = file(packTargetFile, "$state.png.mcmeta")
                            if (mcmetaFile.exists()) {
                                FileUtils.copyFileToDirectory(mcmetaFile, resPackPngFile)
                            }
                        }
                    }

                }
            }
        }
    }

    private fun optiFineGenerate(pluginFile: File, defPath: String,
                                 modelFileName: String, i: String,
                                 state: String, modelInfo: ModelInfoManager,
                                 json: File) {
        var jsonType = "optifine"
        if (ImiPet.loader.getServerInfo().isOldVersionMaterial) {
            jsonType = "mcpatcher"
        }
        val resPackFile = file(pluginFile,
            defPath + i +
                    jsonType + i +
                    "cit" + i +
                    "imipet" + i +
                    modelFileName)
        val propertiesFile = file(resPackFile, "$state.properties")
        if (!propertiesFile.exists()) {
            propertiesFile.parentFile.mkdirs()
            propertiesFile.parentFile.createNewFile()
        }
        propertiesFile.bufferedWriter().use { out ->
            out.write("type=item\n" +
                    "items=minecraft:${getStateMaterial(state, modelInfo)}\n" +
                    "nbt.display.Name=${UnicodeUtil().convert(getStateItemName(state, modelInfo))}\n" +
                    "model=./"+state)
            out.close()
        }
        FileUtils.copyFileToDirectory(json, resPackFile)
    }

    private fun customModelDataGenerate(pluginFile: File, defPath: String,
                                        modelFileName: String, i: String,
                                        state: String, modelInfo: ModelInfoManager,
                                        json: File) {
        val resPackFile = file(pluginFile,
            defPath + i +
                    "models" + i +
                    "imipet" + i +
                    modelFileName)
        if (!resPackFile.exists()) {
            resPackFile.parentFile.mkdirs()
        }
        FileUtils.copyFileToDirectory(json, resPackFile)

        val material = getStateMaterial(state, modelInfo)
        val itemJsonFile = file(pluginFile,
            defPath + i +
                    "models" + i +
                    "item" + i +
                    material+".json")
        if (!itemJsonFile.exists()) {
            itemJsonFile.parentFile.mkdirs()
            itemJsonFile.parentFile.createNewFile()
            // 写入json
            val jsonObject = JsonObject()
            jsonObject.addProperty("parent", "item/handheld")

            val jsonTextures = JsonObject()
            jsonTextures.addProperty("layer0", "item/$material")
            jsonObject.add("textures", jsonTextures)

            val arrayCMD = JsonArray()
            jsonObject.add("overrides", arrayCMD)
            itemJsonFile.bufferedWriter().use { out ->
                out.write(jsonObject.toString())
                out.close()
            }
        }
        val overParse = JsonParser()
        val readerOver = FileReader(itemJsonFile)
        val overObject: JsonObject = overParse.parse(readerOver).asJsonObject
        readerOver.close()
        var arrayOver = overObject.get("overrides").asJsonArray

        if (arrayOver.toString().contains("imipet/$modelFileName/$state")) return

        val cmdData = JsonObject()
        cmdData.addProperty("custom_model_data", getStateItemCMD(state, modelInfo))

        val predicateObject = JsonObject()
        predicateObject.add("predicate", cmdData)

        predicateObject.addProperty("model", "imipet/$modelFileName/$state")

        arrayOver.add(predicateObject)
        if (arrayOver.size() > 1) {
            val listA: MutableList<JsonObject?> =
                ArrayList()
            var sortObject: JsonObject?
            for (jsonIndex in 0 until arrayOver.size()) {
                sortObject = arrayOver.get(jsonIndex) as JsonObject?
                listA.add(sortObject)
            }
            val sortJsonArray = JsonArray()
            val sortItem = "custom_model_data"
            Collections.sort(listA, SortComparator(sortItem))
            for (sortIndex in 0 until listA.size) {
                sortObject = listA[sortIndex]
                sortJsonArray.add(sortObject)
            }
            arrayOver = sortJsonArray
        }
        overObject.add("overrides", arrayOver)

        itemJsonFile.bufferedWriter().use { out ->
            out.write(overObject.toString())
            out.close()
        }
    }

    private fun getStateMaterial(state: String, modelInfo: ModelInfoManager): String {
        return when (state) {
            "idle" -> {
                modelInfo.animationMaterialIdle.name.toLowerCase()
            }
            "walk" -> {
                modelInfo.animationMaterialWalk.name.toLowerCase()
            }
            "attack" -> {
                modelInfo.animationMaterialAttack.name.toLowerCase()
            }
            else -> stateFine("material", modelInfo, state)!!
        }
    }

    private fun getStateItemName(state: String, modelInfo: ModelInfoManager): String {
        return when (state) {
            "idle" -> {
                modelInfo.animationItemNameIdle.toLowerCase()
            }
            "walk" -> {
                modelInfo.animationItemNameWalk.toLowerCase()
            }
            "attack" -> {
                modelInfo.animationItemNameAttack.toLowerCase()
            }
            else -> stateFine("name", modelInfo, state)!!
        }
    }

    private fun getStateItemCMD(state: String, modelInfo: ModelInfoManager): Int {
        return when (state) {
            "idle" -> {
                modelInfo.animationCustomModelDataIdle
            }
            "walk" -> {
                modelInfo.animationCustomModelDataWalk
            }
            "attack" -> {
                modelInfo.animationCustomModelDataAttack
            }
            else -> stateFine("cmd", modelInfo, state)!!.toInt()
        }
    }

    private fun stateFine(type: String, modelInfo: ModelInfoManager, state: String): String? {
        if (state.contains("_")) {
            val sp = state.split("_")
            val skillID = sp[0].toLowerCase()
            val animationID = sp[1]
            for (skill in modelInfo.animationModelList) {
                if (skill.skillModelID != skillID) continue
                for (animation in skill.animationModels) {
                    if (animation.animationID != animationID) continue
                    when (type) {
                        "name" -> {
                            return animation.itemStack.itemMeta!!.displayName.toLowerCase()
                        }
                        "material" -> {
                            return animation.itemStack.type.name.toLowerCase()
                        }
                        "cmd" -> {
                            return animation.itemStack.itemMeta!!.customModelData.toString()
                        }
                    }
                }
            }
        }
        return null
    }

    private fun optiFine(pluginFile: File, defPath: String, modelFileName: String) {
        val i = File.separator
        val resFile = File(pluginFile, getOptiFinePath(defPath) + i + modelFileName)
        if  (!resFile.parentFile.exists()) {
            resFile.parentFile.mkdirs()
        }
    }

    private fun cmd(pluginFile: File, defPath: String, modelFileName: String) {
        val i = File.separator
        val resFile = File(pluginFile, defPath + i + "models" + i + "imipet" + i + modelFileName)
        if  (!resFile.parentFile.exists()) {
            resFile.parentFile.mkdirs()
        }
    }

    private fun getOptiFinePath(defPath: String): String {
        val i = File.separator
        if (ImiPet.loader.getServerInfo().isOldVersionMaterial) {
            return defPath + i + "mcpatcher" + i + "cit" + i + "imipet"
        }
        return defPath + i + "optifine" + i + "cit" + i + "imipet"
    }

    private fun file(file: File, path: String): File {
        return File(file.path + File.separator + path)
    }

    private fun yaml(file: File): FileConfiguration {
        return YamlConfiguration.loadConfiguration(file)
    }
}