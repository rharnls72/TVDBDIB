<template>
  <div class="feed-item">
      <ul> <!-- 받아온 데이터로 반복 돌리자 -->
            <!-- done == 'Y' 일때만 done 이라는 클래스를 지정 -->
          <li v-for="(alert) in alerts" v-bind:key="alert.ano" 
          class="shadow" v-bind:class="{done: !alert.read}" type="button" @click="checkAlert(alert.ano)">

        <div class="form-group row">
            <img class="my-auto col-3 col-sm-3" :src='alert.picture'>
            <span class="alert-content my-auto col-6 col-sm-6">
              <p v-if='alert.atype == 1'>{{alert.subject_name}}님이 나를 팔로우했습니다.</p>
              <p v-if='alert.atype == 2'>{{alert.subject_name}}님이 내 글에 좋아요를 표시했습니다.</p>
              <p v-if='alert.atype == 3'>{{alert.subject_name}}님이 내 글에 댓글을 달았습니다.</p>
              <p v-if='alert.atype == 4'>{{alert.subject_name}}님이 글에서 나를 언급했습니다.</p>
            </span>
            <span class="float-right my-auto col-3 col-sm-3 removeBtn" type="button" @click="deleteTodo(alert.ano)">
                <b-icon-box-arrow-in-up-right font-scale="1.5" class="float-right" v-if="alert.atype >= 2"></b-icon-box-arrow-in-up-right>
                <button class="col-6 col-sm-6 btn btn-danger btn-sm" v-if="alert.atype == 1">거절</button>
                <button class="float-right col-6 col-sm-6 btn btn-primary btn-sm" v-if="alert.atype == 1">승인</button>
            </span>
        </div>
<!--
            <div class="align-center">
                <img src="@/assets/images/profile_default.png">
                {{alert.content}}
            </div>

             <span class="removeBtn" type="button" @click="deleteTodo(alert.no)">
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
    alerts: Array
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
  padding: 0 0.5rem;
  background: white;
  border-radius: 3px;
}
.checkBtn {
  line-height: 45px;
  color: #62acde;
  margin-right: 5px;
}
.removeBtn {
  color: blue;
  padding: 0;
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
.btn{
  height: 5%;
  width: 70%;
}

.alert-content{
  padding: 0;
}

p{
  margin: 5%;
}

</style>