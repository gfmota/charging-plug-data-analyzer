import sys

def prepare_test_plan(number_of_threads):
    base_file = "jmeter/PollingGatewayTest_base.jmx"
    with open(base_file, 'r') as file:
        content = file.read()

    updated_content = content.replace("<NUMBER-OF-THREADS>", str(number_of_threads))

    final_file = "jmeter/PollingGatewayTest.jmx"
    with open(final_file, 'w') as file:
        file.write(updated_content)

    print(f"Replaced '<NUMBER-OF-THREADS>' with '{number_of_threads}' in '{final_file}'.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python script.py <n>")
        sys.exit(1)

    n = int(sys.argv[1])
    prepare_test_plan(n)
    print(f"Generated test plan with {n} threads.")