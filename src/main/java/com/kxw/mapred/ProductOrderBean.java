package com.kxw.mapred;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义Bean,实现序列化
 * Created by kangxiongwei on 2017/5/28.
 */
public class ProductOrderBean implements WritableComparable<ProductOrderBean> {

    private Integer id = 0; //订单编号
    private String createDate = ""; //下单日期
    private String pid = ""; //产品编号
    private Integer amount = 0; //购买数量
    private String pname = ""; //产品名称
    private Integer categaryId = 0; //产品分类
    private Float price = 0f; //产品单价
    private Integer flag = 0; //标志为 订单 0 产品 1

    public ProductOrderBean() {
    }

    public ProductOrderBean(Integer id, String createDate, String pid, Integer amount, Integer flag) {
        this.id = id;
        this.createDate = createDate;
        this.pid = pid;
        this.amount = amount;
        this.flag = flag;
    }

    public ProductOrderBean(String pid, String pname, Integer categaryId, Float price, Integer flag) {
        this.pid = pid;
        this.pname = pname;
        this.categaryId = categaryId;
        this.price = price;
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getCategaryId() {
        return categaryId;
    }

    public void setCategaryId(Integer categaryId) {
        this.categaryId = categaryId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeUTF(createDate);
        dataOutput.writeUTF(pid);
        dataOutput.writeInt(amount);
        dataOutput.writeUTF(pname);
        dataOutput.writeInt(categaryId);
        dataOutput.writeFloat(price);
        dataOutput.writeInt(flag);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        id = dataInput.readInt();
        createDate = dataInput.readUTF();
        pid = dataInput.readUTF();
        amount = dataInput.readInt();
        pname = dataInput.readUTF();
        categaryId = dataInput.readInt();
        price = dataInput.readFloat();
        flag = dataInput.readInt();
    }

    @Override
    public int compareTo(ProductOrderBean o) {
        return 0;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", pid='" + pid + '\'' +
                ", amount=" + amount +
                ", pname='" + pname + '\'' +
                ", categaryId=" + categaryId +
                ", price=" + price;
    }
}
