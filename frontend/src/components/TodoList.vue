<template>
  <div class="Todo-List">
    <h1>Vue Spring Todo List</h1>

    <todo-list-add-item-bar @addTodo="addNewTodo" v-model="newTodo"/>

    <div class="task-list">
      <div
          :class="{task: true, done:item.done}"
          v-for="item in items"
          :key="item.id">

        <todo-list-item
            :task="item"
            @checked="markTaskDone(item.id)"
            @delete="deleteTask(item.id)"
            @update="updateTask(item.id, $event)"/>
      </div>

    </div>

    <todo-list-info
        :task-count="items.length"
        :incomplete-task-count="incompleteTaskCount"
        @clear-all="clearTasks"/>

  </div>
</template>

<script>
import TodoListAddItemBar from "@/components/TodoListAddItemBar";
import TodoListItem from "@/components/TodoListItem";
import TodoListInfo from "@/components/TodoListInfo";
import taskApi from "@/tasks.api";

export default {
  name: 'TodoList',
  components: {TodoListInfo, TodoListItem, TodoListAddItemBar},
  props: {
    msg: String
  },
  data() {
    return {
      items: [],
      newTodo: "",
    }
  },
  computed: {
    incompleteTaskCount() {
      return this.items.filter(item => !item.done).length;
    }
  },
  methods: {
    addNewTodo() {
      const valid = this.validateNewTodo()

      if (valid) {
        taskApi.addNewTask({name: this.newTodo})
        .then(task => {
          this.items.unshift(this.parseTask(task));
          this.newTodo = "";
          this.moveIncompleteItemsUp();
        })
      } else  {
        this.focusOnInput();
      }
    },
    validateNewTodo(id, optString) {
      // optional string means editing existing task
      if (optString) {
        // check it's greater than 1 character and not the same
        return optString.length >= 1 && this.getTaskName(id) !== optString;
      }
      return this.newTodo.length >= 1;
    },

    focusOnInput() {
      this.$refs.newTask.focus();
    },

    deleteTask(id) {
      let index = this.getTaskIndex(id);
      taskApi.deleteTaskById(id)
      .then(res => {
        if (res.success === "true")
          this.items.splice(index, 1)
      })
    },

    clearTasks() {
      // TODO - Add in a notification for confirmation
      this.items.forEach((item, index) => {
        taskApi.deleteTaskById(item.id)
        .then(res => {
          if (res.success === "true")
            this.items.splice(index, 1)
        })
      })
    },

    markTaskDone(id) {
      let index = this.getTaskIndex(id)

      taskApi.markComplete(id, !this.items[index].done)
      .then(task => {
        this.items[index].done = task.complete;
        this.moveIncompleteItemsUp();
      })
      .catch(err => console.log(err))
    },

    updateTask(id, update) {
      let index = this.getTaskIndex(id);

      const valid = this.validateNewTodo(update);

      if (valid) {
        taskApi.updateTaskName(id, update)
        .then(task => {
          this.items[index].name = task.name;
        })
        .catch(err => console.log(err))
      }
      else
        //Sets the field back to what was stored
        this.$forceUpdate();
    },

    moveIncompleteItemsUp() {
      for(let i = 0; i < this.items.length; i++) {
        while (this.items[i].done && this.items[i+1] && !this.items[i+1].done) {
          this.swapItems(i, i+1)
        }
      }

    },

    swapItems(a, b) {
      let temp = this.items[a]
      this.items[a] = this.items[b]
      this.items[b] = temp
    },

    getTaskIndex(id) {
      return this.items.findIndex(item => item.id === id);
    },

    getTaskName(id) {
      return this.items.findIndex(item => item.id === id).name;
    },

    parseTask(task) {
      return {
        id: task.id,
        name: task.name,
        done: task.complete
      }

    }
  },
  beforeMount() {
    taskApi.getTasks()
        .then(tasks => this.items = tasks.map(task => this.parseTask(task)))
        .catch(err => console.log(err))
  }
}
</script>

<style scoped>

.Todo-List {
  display: inline-block;
  border-radius: 10px;
  background-color: var(--color-background-panel);
  padding: 1rem 2rem;
  box-shadow: rgba(0, 0, 0, 0.05) 0 6px 24px 0, rgba(0, 0, 0, 0.08) 0 0 0 1px;
}

h1 {
  margin-bottom: 1rem;
  text-align: left;
}

.task-list {
  margin-top: 1rem;
}

.Todo-List-Info h3 {
  align-self: center;
}

</style>
