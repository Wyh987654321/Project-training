export let student = {
  stu_id:{
    text:"学号",
    type: 'string'
  },
  stu_name:{
    text:"姓名",
    type: 'string'
  },
  stu_profession:{
    text:"专业",
    type: 'string'
  },
  stu_phone:{
    text:"电话",
    type: 'string'
  },
  stu_email:{
    text:"邮箱",
    type: 'string'
  },
}

export let reply = {
  rep_time: {
    text: "答辩时间",
    type: 'string'
  },
  rep_address: {
    text: "答辩地点",
    type: 'string'
  },
  rep_teacher: {
    text: "答辩老师",
    type: 'string'
  },
  rep_group: {
    text: "答辩小组",
    type: 'string'
  },
}
export let teacher = {
  tea_id:{
    text:"教师号",
    type: 'string'
  },
  tea_name:{
    text:"姓名",
    type: 'string'
  },
  tea_phone:{
    text:"电话",
    type: 'string'
  },
  tea_email:{
    text:"邮箱",
    type: 'string'
  },
}

export let readFile = function (file){
  return new Promise(resolve => {
    let reader = new FileReader()
    reader.readAsBinaryString(file)
    reader.onload = ev => {
      resolve(ev.target.result)
    }
  })
}

export let unique = function (arr) {
    return Array.from(new Set(arr))
}

export let transformData = function (data){
  let arr = []
  data.forEach(item =>{
    let obj = {}
    for (let key in student){
      // if (!character.hasOwnProperty(key)) break
      if(!Object.prototype.hasOwnProperty.call(student,key)) break
      let v = student[key]
      let text = v.text
      let type = v.type
      v = item[text] || ""
      type === "string"?(v = String(v)):null
      type === "number"?(v = Number(v)):null
      obj[key] = v
    }
    arr.push(obj)
    for (let i=0;i<arr.length;i++){
      arr[i].stu_password =arr[i].stu_id+arr[i].stu_phone.substr(0,4)
      // arr[i].stu_group =""
      // arr[i].stu_grade1 =0
      // arr[i].stu_grade2 =0
    }
  }
  )
  return arr
}

export let transformData_tea = function (data){
  let arr = []
  data.forEach(item =>{
        let obj = {}
        for (let key in teacher){
          // if (!character.hasOwnProperty(key)) break
          if(!Object.prototype.hasOwnProperty.call(teacher,key)) break
          let v = teacher[key]
          let text = v.text
          let type = v.type
          v = item[text] || ""
          type === "string"?(v = String(v)):null
          type === "number"?(v = Number(v)):null
          obj[key] = v
        }

        arr.push(obj)
        for (let i=0;i<arr.length;i++){
          arr[i].tea_password =arr[i].tea_id+arr[i].tea_phone.substr(0,4)
        }
      }
  )
  return arr
}
export let transformData_rep = function (data){
  let arr = []
  data.forEach(item =>{
        let obj = {}
        for (let key in reply){
          // if (!character.hasOwnProperty(key)) break
          if(!Object.prototype.hasOwnProperty.call(reply,key)) break
          let v = reply[key]
          let text = v.text
          let type = v.type
          v = item[text] || ""
          type === "string"?(v = String(v)):null
          type === "number"?(v = Number(v)):null
          obj[key] = v
          obj.rep_time = obj.rep_time.replaceAll("/","-")
          console.log(obj.rep_time)
        }
        arr.push(obj)

      }
  )
  return arr
}

export let matchType = function (filename){
  let suffix = ''
  let result = ''
  try {
    let fileArr = filename.split('.')
    suffix = fileArr[fileArr.length-1]
  } catch (err) {
    suffix = ''
  }
  if (!suffix) {
    result = false
    return result
  }
  let imglist = ['jpg','png']
  result = imglist.some(function (item){
    return item === suffix
  })
  if (result) {
    result = 'image'
    return result
  }

  let wordlist = ['doc','docx']
  result = wordlist.some(function (item){
    return item === suffix
  })
  if (result) {
    result = 'word'
    return result
  }

  let execllist = ['xls','xlsx']
  result = execllist.some(function (item){
    return item === suffix
  })
  if (result) {
    result = 'execl'
    return result
  }

  let pdflist = ['pdf']
  result = pdflist.some(function (item){
    return item === suffix
  })
  if (result) {
    result = 'pdf'
    return result
  }

  let ziplist = ['zip','rar']
  result = ziplist.some(function (item){
    return item === suffix
  })
  if (result) {
    result = 'zip'
    return result
  }

  let txtlist = ['txt']
  result = txtlist.some(function (item){
    return item === suffix
  })
  if (result) {
    result = 'txt'
    return result
  }
}