<template>
  <div>
    <div class="head">
      <div class="fix">&nbsp;</div>
      <div class="txt">
        <a-breadcrumb class="site">
          <a-breadcrumb-item>在线考试系统</a-breadcrumb-item>
          <a-breadcrumb-item>考试列表</a-breadcrumb-item>
        </a-breadcrumb>
        <div class="msg">
          考试列表
        </div>
        <p class="des" style="font-size: 14px;color: rgba(0,0,0,.65)">{{ description }}</p>
      </div>
      <div class="extra-img">
        <img v-if="typeof extraImage !== 'undefined'" :src="extraImage"/>
      </div>
    </div>

    <div class="examlist">
      <a-card :bordered="false">
        <div class="NewOne">
          <a-button type="primary" icon="plus" @click="$refs.createExamModal.create()">新建</a-button>&nbsp;

          <a-form layout="inline" @submit="handleSubmit">
            <a-form-item>
              <a-input
                  placeholder="搜索名称"
                  v-model="searchData"
              >
                <a-icon slot="prefix" type="search" style="color:rgba(0,0,0,.25)"/>
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" html-type="submit" @click="search">
                搜索
              </a-button>
            </a-form-item>
          </a-form>
          <!--          <a-form>-->
          <!--            <a-form-item>-->
          <!--              <a-input-->
          <!--                  v-decorator="['note', { rules: [{ required: true, message: 'Please input your note!' }] }]"-->
          <!--                  placeholder="Select a option and change input text above"-->
          <!--              />-->
          <!--            </a-form-item>-->
          <!--            <a-button type="primary" html-type="submit">-->
          <!--              Submit-->
          <!--            </a-button>-->
          <!--          </a-form>-->
        </div>

        <a-table
            :columns="columns"
            :data-source="dataSource"
            :pagination="pagination"
            :loading="loading"
            rowKey="serial"
            class="ExamTable"
        >

          <template #tags='tags'>
            <a-tag
                :key="tags"
                :color="tags === 'hard' ? 'volcano' : tags === 'mid'? 'geekblue' : 'green'"
            >
              {{ tags.toUpperCase() }}
            </a-tag>
          </template>

          <template #actionTest='text,record'>
            <a-button @click="handleJoin(record.serial)">参加考试</a-button>
          </template>

        </a-table>
        <!-- ref是为了方便用this.$refs.modal直接引用 -->
        <new-exam ref="createExamModal" @ok="handleOk"/>

      </a-card>

    </div>

  </div>

</template>

<script>
import request from "@/utils/request";
import NewExam from "@/components/exam/components/NewExam";

export default {
  name: "ExamList",
  components: {
    NewExam
  },
  data() {
    return {
      searchData:'',
      description: '您可以选择一个考试',
      extraImage: 'https://gw.alipayobjects.com/zos/rmsportal/RzwpdLnhmvDJToTdfDPe.png',
      pagination: {
        disabled: false,  //禁用分页
        hideOnSinglePage: false, //只有一页时是否隐藏分页器
        pageSize: 10, //每页条数，所有页设置统一的条数
        pageSizeOptions: ['10', '20', '30'], //每页显示的条数，每页设置不同的条数
        total: 100, //数据总数
      },
      loading: false,
      columns: [
        {
          title: '序号',
          dataIndex: 'serial',
          ellipsis: true,
          sorter: true
        },
        {
          title: '名称',
          dataIndex: 'name',
          ellipsis: true,
        },
        {
          title: '科目',
          dataIndex: 'subject',
          ellipsis: true,
          filters: [
            {text: '数学', value: 'math'},
            {text: '物理', value: 'physics'},
            {text: '计算机', value: 'computer'},
          ],
        },
        {
          title: '难度',
          dataIndex: 'difficulty',
          ellipsis: true,
          scopedSlots: {customRender: 'tags'},    //开启插槽，插槽名为`subCH`
          filters: [
            {text: '难', value: 'hard'},
            {text: '中', value: 'mid'},
            {text: '易', value: 'easy'},
          ],
        },
        {
          title: '总分数',
          dataIndex: 'score',
          ellipsis: true,
        },
        {
          title: '时长(min)',
          dataIndex: 'duration',
          ellipsis: true,
        },
        {
          title: '操作',
          dataIndex: 'action',
          ellipsis: true,
          scopedSlots: {customRender: 'actionTest'},
        }
      ],
      dataSource: [
        {
          serial: 1,
          name: '三角函数',
          subject: 'math',
          difficulty: 'hard',
          score: 100,
          duration: 90
        }
      ],
    }
  },
  mounted() {
    this.loadAll()
  },
  methods: {
    search() {
      request.get("/exam/all",this.searchData)
          .then(res => {
            if (res.code === '0') {
              this.dataSource = res.data
              this.pagination.total = this.dataSource.length
              this.loading = false;
              this.$notification.success({
                message: '成功',
                description: `搜索成功`
              })
            } else {
              this.$notification.error({
                message: '搜索失败',
                description: res.msg
              })
            }
          })
    },
    handleJoin(id) {
      console.log("serial是")
      console.log(id)
      const routeUrl = this.$router.resolve({
        path: `/exam/${id}`
      })
      window.open(routeUrl.href, '_blank')
    },
    handleOk() {
      this.loadAll();
    },
    loadAll() {
      this.loading = true;
      request.get("/exam/all")
          .then(res => {
            if (res.code === '0') {
              this.dataSource = res.data
              this.pagination.total = this.dataSource.length
              this.loading = false;
            } else {
              this.$notification.error({
                message: '获取全部考试的列表失败',
                description: res.msg
              })
            }
          })
    }
  }
}
</script>

<style lang="less" scoped>
.head {
  color: rgba(0, 0, 0, .85);
  font-size: medium !important;
  background: white;
  margin-top: 0;

  .fix {
    margin-top: 0;
    color: white;
  }

  .txt {
    margin-left: 150px;

    .msg {
      margin-top: 10px;
      font-size: 22px;
    }

    .des {
      margin-top: 5px;
    }
  }

  .extra-img {
    margin-top: -60px;
    text-align: center;
    width: 195px;
    position: absolute;
    right: 250px;
    top: 120px;

    img {
      margin-right: 50px;
      width: 100%;
      alignment: right;
    }
  }
}

.examlist {
  text-align: center;
  margin: 0 auto;
  width: 85%;

  .NewOne {
    text-align: left;
  }
}
</style>