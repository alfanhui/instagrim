package uk.ac.dundee.computing.swmh.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import static com.datastax.driver.core.TypeCodec.set;
import com.datastax.driver.core.utils.Bytes;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;
import uk.ac.dundee.computing.swmh.instagrim.stores.Pic;
import uk.ac.dundee.computing.swmh.instagrim.lib.Convertors;
//import uk.ac.dundee.computing.aec.stores.TweetStore;

public class PicModel {

    Cluster cluster;

    public void PicModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void insertPic(byte[] b, String type, String name, String user, boolean profilePicture) {
        try {
            Convertors convertor = new Convertors();

            String types[]=Convertors.SplitFiletype(type);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int length = b.length;
            java.util.UUID picid = convertor.getTimeUUID();
            
            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            Boolean success = (new File("/var/tmp/instagrim/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/instagrim/" + picid));

            output.write(b);
            byte []  thumbb = picresize(picid.toString(),types[1]);
            int thumblength= thumbb.length;
            ByteBuffer thumbbuf=ByteBuffer.wrap(thumbb);
            byte[] processedb = picdecolour(picid.toString(),types[1]);
            ByteBuffer processedbuf=ByteBuffer.wrap(processedb);
            int processedlength=processedb.length;
            Session session = cluster.connect("instagrim");
            Date DateAdded = new Date();
            
            PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image,thumb,processed, user, interaction_time,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
            if(!profilePicture){ //If profile picture, do not added it to user's pictures
                PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
                BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);
                session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
            }else{
                PreparedStatement ps = session.prepare("update userprofiles set profile_picid =? where login =?");
                BoundStatement boundStatement = new BoundStatement(ps);
                session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                        picid, user));
            }
            BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
            session.execute(bsInsertPic.bind(picid,buffer,thumbbuf,processedbuf,user,DateAdded,length,thumblength,processedlength,type,name));
            session.close();

        } catch (IOException ex) {
            System.out.println("Error --> " + ex);
        }
    }
    
    public void setComment(String picid, String comment, String username, String timeStamp){
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select comments from pics where picid =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        java.util.UUID.fromString(picid)));
        String comments = "";
        for(Row row: rs){
            if(row.getString("comments")!=null)
                comments += row.getString("comments");
        }
        comments += comment + "~@!  " + username + "~@!  " + timeStamp + "~@!";
        PreparedStatement psInsertCommentPic = session.prepare("update pics set comments = ? where picid=?");
        BoundStatement bsInsertCommentPic = new BoundStatement(psInsertCommentPic);
        session.execute(
                bsInsertCommentPic.bind(
                    comments, java.util.UUID.fromString(picid)));
    }
    
    public String[] getComment(String picid){
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select comments from pics where picid =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        java.util.UUID.fromString(picid)));
        if (rs.isExhausted()) {
            return null;
        }else{
            String[] comments = null;
            for(Row row: rs){
                if(row.getString("comments")!=null)
                    comments = row.getString("comments").split("~@!");
            }
            return comments;  
        }    
    }
    


    public byte[] picresize(String picid,String type) {
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            BufferedImage thumbnail = createThumbnail(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }
    
    public byte[] picdecolour(String picid,String type) {
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            //BufferedImage resizedBI = org.imgscalr.Scalr.resize(BI, 300);
            BufferedImage processed = createProcessed(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public static BufferedImage createThumbnail(BufferedImage img) {
        img = resize(img, Method.SPEED, Mode.FIT_TO_HEIGHT, 250, OP_ANTIALIAS, OP_GRAYSCALE);
        // Let's add a little border before we return result.
        return pad(img, 1);
    }
    
   public static BufferedImage createProcessed(BufferedImage img) {
        int Width=img.getWidth()-1;
        img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_GRAYSCALE);
        return pad(img, 2);
    }
   
    public java.util.LinkedList<Pic> getPicsForUser(String User) {
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                Pics.add(pic);

            }
        }
        return Pics;
    }

    public String getRandomPic(){
        Session session = cluster.connect("instagrim");
        Random rand = new Random();
        PreparedStatement ps = session.prepare("select picid from pics");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet rs = session.execute(boundStatement);
        if (rs.isExhausted()) {
            return null;
        }else{
            int  n = rand.nextInt(10) + 1;
            java.util.UUID uuid = null;
            for(Row row: rs){
                uuid = row.getUUID("picid");
                if(n == 1)
                    break;
            }
            return uuid.toString();
        }
    }
    
    
    public Pic getPic(int image_type, java.util.UUID picid) {
        Session session = cluster.connect("instagrim");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        try {
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;
         
            if (image_type == Convertors.DISPLAY_IMAGE) {
                
                ps = session.prepare("select image,imagelength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_THUMB) {
                ps = session.prepare("select thumb,imagelength,thumblength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                ps = session.prepare("select processed,processedlength,type from pics where picid =?");
            }
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
                System.out.println("No Images returned");
                return null;
            } else {
                for (Row row : rs) {
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                    } else if (image_type == Convertors.DISPLAY_THUMB) {
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");
                
                    } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                    }
                    
                    type = row.getString("type");

                }
            }
        } catch (Exception et) {
            System.out.println("Can't get Pic" + et);
            return null;
        }
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;

    }

}
