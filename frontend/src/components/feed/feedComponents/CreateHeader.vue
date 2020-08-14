<template>
  <div>
    <div class="col d-flex justify-content-between align-items-center mt-3 mb-5">
      <b-icon @click="moveback" icon="chevron-left" font-scale="1.5"></b-icon>
      <b-icon @click="submit" icon="check-square" font-scale="1.4"></b-icon>
    </div>
  </div>
</template>

<script>
export default {
  name: "CreateHeader",
  methods: {
    submit() {
      this.$emit('submit')
    },
    moveback() {
      this.$router.go(-1)
    }
  }
}
</script>

<style>

</style>