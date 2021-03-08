<template>
  <div id="app">
    <todo-list/>
  </div>
</template>

<script>
import TodoList from "@/components/TodoList";

export default {
  name: 'App',
  components: {
    TodoList,
  }
}
</script>

<style>
 @import './styles/variables.css';

 .Todo-List {
   position: absolute;
   top: 40%;
   left: 50%;
   transform: translate(-50% ,-50%);
 }

 body {
 background: linear-gradient(var(--color-vue-green), #9198e5);
 }

</style>
