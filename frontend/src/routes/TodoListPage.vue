<template>
  <div class="Todo-List-Page">
    <todo-list/>
  </div>
</template>

<script>

import TodoList from "@/components/TodoList";
import taskApi from '../tasks.api';

export default {
  name: "TodoListPage",
  components: {TodoList},
  data() {
    return {
      response: [],
      errors: []
    }
  },
  methods: {
    callRestService() {
      taskApi.getTasks()
          .then(tasks => console.log(tasks))
          .catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>

.Todo-List {
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50% ,-50%);
}

.Todo-List-Page {
  height: 100vh;
  width: 100vw;
  background: linear-gradient(var(--color-vue-green), #9198e5);
}

</style>