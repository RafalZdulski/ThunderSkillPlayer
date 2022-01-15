package com.thunderfinesse.thunderskillfetcher.player.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.thunderfinesse.thunderskillfetcher.player.data.Vehicle;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/** upsert one given list of vehicle stats into given collection*/
public class DBWriterThread implements Runnable{

    private List<Vehicle> list;
    private MongoCollection<Document> collection;
    private CountDownLatch upsertLatch;

    public DBWriterThread(MongoCollection<Document> collection, List<Vehicle> list, CountDownLatch upsertLatch){
        this.collection = collection;
        this.list = list;
        this.upsertLatch = upsertLatch;
    }

    @Override
    public void run() {
        push(list);
        upsertLatch.countDown();
    }

    private void push(List<Vehicle> list){
        for (var v : list){
            Bson filter = Filters.eq("_id",v.getId());
            Bson update = v.toUpdate();
            UpdateOptions options = new UpdateOptions().upsert(true).bypassDocumentValidation(true);
            collection.updateOne(filter, update, options);

            //  System.out.printf("%30s inserted to db.%s\n",v.getId(),collection);
        }

    }
}
