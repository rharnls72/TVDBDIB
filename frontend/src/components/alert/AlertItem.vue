<template>
  <div class="feed-item" style="margin-top: 15%;">
      <ul> <!-- 받아온 데이터로 반복 돌리자 -->
            <!-- done == 'Y' 일때만 done 이라는 클래스를 지정 -->
          <li v-for="(todoItem) in todolists" v-bind:key="todoItem.no" 
          class="shadow" v-bind:class="{done: todoItem.done == 'Y'}">

        <div class="form-group row">
            <img class="my-auto col-3 col-sm-3" src="@/assets/images/profile_default.png">
            <span class="my-auto col-8 col-sm-8">{{todoItem.content}}</span>
            <span class="my-auto col-1 col-sm-1 removeBtn" type="button" @click="deleteTodo(todoItem.no)">
                <b-icon-box-arrow-in-up-right v-if="todoItem.done == 'Y'"></b-icon-box-arrow-in-up-right>
                <b-icon-person-plus-fill v-if="todoItem.done == 'N'" class="removeBtn"></b-icon-person-plus-fill>
            </span>
        </div>
<!--
            <div class="align-center">
                <img src="@/assets/images/profile_default.png">
                {{todoItem.content}}
            </div>

             <span class="removeBtn" type="button" @click="deleteTodo(todoItem.no)">
                <i class="far fa-trash-alt"></i> 쓰레기통 그림 
            </span>
-->
        </li>

      </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
//import commentcss from "@/components/css/feed/comment-list.scss";

export default {
  name: 'AlertItem',
  data: () => {
    return {
      defaultProfile
    };
  },
  props: {
    todolists: Array
  }
}
</script>

<style scoped>
ul {
  list-style-type: none;
  padding-left: 0px;
  margin-top: 0%;
  text-align: left;
}
li {
  display: flex;
  min-height: 30px;
  height: 70px;
  line-height: 20px;
  margin: 0.5rem 0;
  padding: 0 0.9rem;
  background: white;
  border-radius: 5px;
}
.checkBtn {
  line-height: 45px;
  color: #62acde;
  margin-right: 5px;
}
.removeBtn {
  color: blue;
}

.list-item {
  display: inline-block;
  margin-right: 5px;
}
.list-move {
  transition: transform 1s;
}
.list-enter-active,
.list-leave-active {
  transition: all 1s;
}
.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
.done{
  background-color: lightgoldenrodyellow;
}

img{
    margin-right: -5%;
    vertical-align: middle;
}
</style>