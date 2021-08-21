<template>
    <div>
        <button @click="copyText(code)">
            复制到剪切板
        </button>
    </div>
</template>

<script>
    export default {
        name: "btnCopy",
        methods:{
            copyText(value) {
                document.designMode = 'on';
                const input = document.createElement('input');
                document.body.appendChild(input);
                input.setAttribute('value', value);
                input.setAttribute('readonly', 'readonly')
                input.select();
                try {
                    var successful = document.execCommand('copy');
                    var msg = successful ? 'successful' : 'unsuccessful';

                    if(!successful) {
                        throw new Error("copy failed");
                    }

                    alert("复制成功！");
                } catch (err) {
                    alert("复制失败，请重试或手动复制");
                }
                document.body.removeChild(input);
                document.designMode = 'off'
            }
        },
        props:{code: String}
    }
</script>

<style scoped>

</style>