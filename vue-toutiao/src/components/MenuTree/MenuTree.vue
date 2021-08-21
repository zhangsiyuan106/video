<!--菜单组件-->
<template>
  <div>

      <template v-for="menu in menuList">
        <el-submenu :disabled="menu.disabled" :index="menu.id+''" :key="menu.id+''" v-if="menu.children.length>0" @click="savePath(menu.url)">
          <template slot="title">
            <i :class="menu.icon"></i>
            <span slot="title">{{menu.menuName}}</span>
          </template>
          <MenuTree :menu-list="menu.children"></MenuTree>
        </el-submenu>
        <el-menu-item v-else :disabled="menu.disabled" :index="menu.url+''" :route="menu.url" :key="menu.id+''">
          <i :class="menu.icon"></i>
          <span slot="title">{{menu.menuName}}</span>
        </el-menu-item>
      </template>
    </div>
</template>

<script>
export default {
  name: "MenuTree",
  props:['menuList'],
  methods:{
    savePath(path) {
      window.sessionStorage.setItem("activePath", path);
      this.activePath = path;
    },
  }
}
</script>

<style scoped>

</style>
