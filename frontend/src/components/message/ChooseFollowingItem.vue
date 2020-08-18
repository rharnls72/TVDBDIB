<template>
  <div class="feed-item">
      <ul> <!-- 받아온 데이터로 반복 돌리자 -->
            <!-- done == 'Y' 일때만 done 이라는 클래스를 지정 -->
          <li v-for="(following) in followings" v-bind:key="following.uno" 
          class="shadow" type="button">

        <div class="form-group row" style="width: 100%;">
          <!-- 이미지 바인딩 어떻게...? 잘 안된다 -->
            <img class="my-auto col-3 col-sm-3" src="@/assets/images/profile_default.png">
            <span class="user-content my-auto col-6 col-sm-6">
              {{following.nick_name}}
            </span>
        </div>
        <button @click.stop.prevent="chooseUser(following)" class="float-right">
              <b-icon icon="plus-circle" ></b-icon>
              </button>
        </li>

      </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
export default {
  name: 'ChooseFollowingItem',
  data: () => {
    return {
      defaultProfile
    };
  },
  props: {
    followings: Array,
    choosed: Array,
  },
  methods:{
    chooseUser(following){
      this.choosed.push(following);
    }
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
    margin-right: 0%;
    vertical-align: middle;
}
.btn{
  height: 5%;
  width: 90%;
}

.user-content{
  padding: 0;
}

p{
  margin: 5%;
}

.hidden{
    display: none;
}

</style>
