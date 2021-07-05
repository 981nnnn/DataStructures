package com.xb.LinkedList;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @ClassName SingleLinkedListDemo
 * @Description TODO
 * @Author xb
 * @Date 2021/7/1 11:14
 * @Version 1.0
 **/
public class SingleLinkedListDemo {

  public static void main(String[] args) {
    //进行测试
    //先创建节点
    HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
    HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
    HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
    HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

    //创建要给链表
    SingleLinkedList singleLinkedList = new SingleLinkedList();


    //加入
//    singleLinkedList.add(hero1);
//    singleLinkedList.add(hero4);
//    singleLinkedList.add(hero2);
//    singleLinkedList.add(hero3);
    singleLinkedList.addByOrder(hero1);
    singleLinkedList.addByOrder(hero4);
    singleLinkedList.addByOrder(hero3);
    singleLinkedList.addByOrder(hero2);
    HeroNode lastKNode = singleLinkedList.findLastKNode(singleLinkedList.head, 1);
    System.out.println(lastKNode);
    singleLinkedList.reversePrint(singleLinkedList.head);


    // 测试链表的有效节点个数
    int length = singleLinkedList.getLength(singleLinkedList.head);
    System.out.println("有效节点的个数" + length);


    HeroNode head = singleLinkedList.getHead();
    singleLinkedList.reverseList(head);


    // 测试一下单链表的反转功能
    System.out.println("原来链表的情况~~");
    singleLinkedList.list();

    HeroNode updateHero2 = new HeroNode(2, "卢俊义", "XIAO玉麒麟");
    singleLinkedList.update(updateHero2);
    // 测试一下单链表的反转功能
    System.out.println("更新一个后，链表的情况~~");
    singleLinkedList.list();


    singleLinkedList.del(3);

    // 测试一下单链表的反转功能
    System.out.println("删除一个后，链表的情况~~");
    singleLinkedList.list();
  }

  // 定义HeroNode
  static class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next; //指向下一个节点

    //构造器
    public HeroNode(int no, String name, String nickname) {
      this.no = no;
      this.name = name;
      this.nickname = nickname;
    }

    //为了显示方法，我们重新toString
    @Override
    public String toString() {
      return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
    }

  }

  static class SingleLinkedList {
    // 初始化一个头节点
    HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
      return head;
    }

    public void add(HeroNode node) {
      HeroNode temp = head;
      while (true) {
        if (temp.next == null) {
          break;
        }
        temp = temp.next;
      }
      //当退出while 循环的时候，temp 就指向链表的最后
      // 将最后这个节点的next，指向新的节点
      temp.next = node;
    }

    /**
     * 循环遍历链表
     */
    public void list() {
      if (head.next == null) {
        System.out.println("链表为空");
        return;
      }
      HeroNode temp = head.next;
      while (true) {
        if (temp == null) {
          break;
        }
        System.out.println(temp);
        temp = temp.next;
      }
    }

    /**
     * 删除节点 1、head 不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点 2、说明我们再比较的时候，是temp.next.no 和 需要删除的节点的no 比较
     */
    public void del(int no) {
      HeroNode temp = head;
      boolean flag = false;
      while (true) {
        // 已经到链表的最后
        if (temp.next == null) {
          break;
        }
        if (temp.next.no == no) {
          flag = true;
          break;
        }
        temp = temp.next;
      }
      if (flag) {
        temp.next = temp.next.next;
      } else {
        System.out.println("删除的节点不存在");
      }
    }

    /**
     * 更新节点数据
     */
    public void update(HeroNode node) {
      HeroNode temp = head;
      for (; ; ) {
        if (temp.next == null) {
          break;
        }
        if (temp.no == node.no) {
          temp.name = node.name;
          temp.nickname = node.nickname;
          break;
        }
        temp = temp.next;
      }
    }

    /**
     * 按照no的顺序，添加
     */
    public void addByOrder(HeroNode heroNode) {
      //因为头节点不能动，因此我们仍然通过一个辅助指针(变量)来帮助找到添加的位置
      //因为单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
      HeroNode temp = head;
      boolean flag = false; // flag标志添加的编号是否存在，默认为false
      while (true) {
        if (temp.next == null) {//说明temp已经在链表的最后
          break; //
        }
        if (temp.next.no > heroNode.no) { //位置找到，就在temp的后面插入
          break;
        } else if (temp.next.no == heroNode.no) {//说明希望添加的heroNode的编号已然存在

          flag = true; //说明编号存在
          break;
        }
        temp = temp.next; //后移，遍历当前链表
      }
      //判断flag 的值
      if (flag) { //不能添加，说明编号存在
        System.out.printf("准备插入的英雄的编号 %d 已经存在了, 不能加入\n", heroNode.no);
      } else {
        //插入到链表中, temp的后面
        heroNode.next = temp.next;
        temp.next = heroNode;
      }
    }

    /**
     * 反转链表，head是头节点
     */
    public void reverseList(HeroNode head) {
      // 如果头节点的下一个节点时候，说明没有
      // 如果头节点的下一个节点的下一个节点，说明只有一个
      if (head.next == null || head.next.next == null) {
        return;
      }
      HeroNode cur = head.next;
      HeroNode next = null; // 指向当前节点（cur）的下一个节点
      HeroNode reverseHead = new HeroNode(0, "", "");
      while (cur != null) {
        next = cur.next;//先暂时保存当前节点的下一个节点，因为后面需要使用
        cur.next = reverseHead.next; // 将cur的下一个节点指向新的链表的最前端
        reverseHead.next = cur; // 将cur连接到新的链表上
        cur = next;
      }
      head.next = reverseHead.next;
    }

    /**
     * 求链表中的有效节点个数 思路： 从头节点开始，判断下一个节点是否是null，如果不是null，length+1； 如果下一个节点是空，直接返回length（初始长度0）
     */
    public int getLength(HeroNode node) {
      int length = 0;
      if (node.next == null) {
        return length;
      }
      HeroNode cur = node.next;
      while (cur != null) {
        length++;
        cur = cur.next;
      }
      return length;
    }

    /**
     * 查询倒数第k个的节点 思路： 先从头开始遍历，得到链表的长度 getLength 得到size以后，我们从头开始的第一个开始遍历（size-index），就可以得到
     */
    public HeroNode findLastKNode(HeroNode node, int k) {
      if (node.next == null) {
        return null;
      }
      int length = 0;
      HeroNode cur = node.next;
      while (cur != null) {
        length++;
        cur = cur.next;
      }
      if (k > length) {
        System.out.println("k的大于链表的长度");
        return null;
      }
      HeroNode result = node.next;
      for (int i = 0; i < length - k; i++) {
        result = result.next;
      }
      return result;
    }

    /**
     * 从尾到头开始打印链表
     */
    public void reversePrint(HeroNode node) {
      if (node.next == null) {
        return;
      }
      HeroNode cur = node.next;
      Stack<HeroNode> stack = new Stack<>();
      while (cur != null) {
        stack.push(cur);
        cur = cur.next;
      }
      while (!stack.isEmpty()) {
        HeroNode pop = stack.pop();
        System.out.println(pop);
      }
    }
  }
}

