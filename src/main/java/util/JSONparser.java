package util;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import model.Progress;

import java.lang.reflect.Type;

public class JSONparser {

    public static String toJSON(Object object){
        Gson gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
        return gson.toJson(object);
    }

    public static String ProgresstoJSON(Object object){
        GsonBuilder builder = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy());
        builder.registerTypeAdapter(Progress.class, new ProgressAdapter());
        Gson gson = builder.create();
        return gson.toJson(object);
    }

    private static class AnnotationExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(Expose.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

    public static class ProgressAdapter implements JsonSerializer<Progress> {
        @Override
        public JsonElement serialize(Progress progress, Type type, JsonSerializationContext jsc) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", progress.getId());
            jsonObject.addProperty("lesson_id", progress.getLesson().getId());
            jsonObject.addProperty("user_id", progress.getUser().getId());
            jsonObject.addProperty("taskPassedNumber", progress.getTaskPassedNumber());
            jsonObject.addProperty("isDone", progress.isDone());
            return jsonObject;
        }
    }
}